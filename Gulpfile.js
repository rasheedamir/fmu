(function() {
    'use strict';

    var config = require('./Gulpconfig')();
    var gulp = require('gulp');
    var args = require('yargs').argv;
    var del = require('del');
    var $ = require('gulp-load-plugins')({
        lazy: true
    });

    gulp.task('clean-styles', function() {
        clean(config.tmp + '/styles');
    });

    gulp.task('clean-fonts', function() {
        clean(config.dist + '/fonts');
    });

    gulp.task('clean-images', function() {
        clean(config.dist + '/images');
    });

    gulp.task('clean-template', function() {
        clean(config.templatecache);
    });

    gulp.task('clean-all',['clean-template'], del.bind(null, [config.tmp, config.dist]));

    gulp.task('sass', ['clean-styles'], function() {
        log('Processing and compiling SCSS to CSS');
        return gulp.src(config.sassfiles)
            .pipe($.if(args.verbose, $.print()))
            .pipe($.plumber())
            .pipe($.sourcemaps.init())
            .pipe($.sass({
                style: 'compressed',
                includePaths: require('node-bourbon').includePaths,
                sourcemap: true,
            }))
            .pipe($.autoprefixer('last 2 versions', '> 5%'))
            .pipe($.sourcemaps.write('/'))
            .pipe(gulp.dest(config.tmp + '/styles'));
    });

    gulp.task('jshint', function() {
        var files = config.jsfiles.slice();
        files.push('!' + config.appPath + '/common/translations/**');
        files.push('!' + config.appPath + '/common/templatecache/**');

        return gulp.src(files)
            .pipe($.if(args.verbose, $.print()))
            .pipe($.jshint())
            .pipe($.jshint.reporter('jshint-stylish', {
                verbose: true
            }))
            .pipe($.jshint.reporter('fail'));
    });

    gulp.task('html', ['wiredep'], function() {
        log('Processing index.html and minifying css/js dependencies');
        var assets = $.useref.assets({
            searchPath: '{.tmp,' + config.appPath + '}'
        });
        var jsfilter = $.filter('**/*.js');
        var cssfilter = $.filter('**/*.css');

        return gulp.src(config.index)
            .pipe(assets)
            .pipe(jsfilter)
            .pipe($.ngAnnotate())
            .pipe($.uglify())
            .pipe(jsfilter.restore())
            .pipe(cssfilter)
            .pipe($.csso())
            .pipe(cssfilter.restore())
            .pipe(assets.restore())
            .pipe($.useref())
            .pipe(gulp.dest(config.dist));
    });

    gulp.task('templatecache', ['clean-template'], function() {
        log('Generate templatecache');
        gulp.src([config.appPath + '/**/*.html', '!' + config.index])
            .pipe($.plumber())

        .pipe($.if(args.verbose, $.print()))
            .pipe($.angularTemplatecache('templatecache.module.js', {
                standalone: true,
                module: 'templatecache'
            }))
            .pipe(gulp.dest(config.templatecache));
    });

    gulp.task('images', ['clean-images'], function() {
        log('Minifying images');
        return gulp.src(config.imagefiles)
            .pipe($.if(args.verbose, $.print()))
            .pipe($.cache($.imagemin({
                progressive: true,
                interlaced: true
            })))
            .pipe(gulp.dest(config.dist + '/common/images'));
    });

    gulp.task('fonts', ['clean-fonts'], function() {
        log('Extract and moving fonts to dist');
        return gulp.src(config.fonts)
            .pipe($.if(args.verbose, $.print()))
            .pipe($.flatten())
            .pipe(gulp.dest(config.dist + '/fonts'));
    });

    gulp.task('extras', function() {
        log('Copying files to dist');
        return gulp.src([
                '!' + config.appPath + '/index.html',
                '!' + config.dist + '/**',
                '!' + config.appPath + '/dependencies/**',
                config.appPath + '/*.{ico,txt,htaccess}',
                config.appPath + '/.htaccess'
            ])
            .pipe($.if(args.verbose, $.print()))
            .pipe(gulp.dest(config.dist));
    });

    gulp.task('extract-pot', function() {
        log('Extract texts from html/js files');
        return gulp.src(config.translationfiles)
            .pipe($.if(args.verbose, $.print()))
            .pipe($.angularGettext.extract('translation.pot', {}))
            .pipe(gulp.dest(config.translationfolder));
    });

    gulp.task('compile-po', function() {
        log('Compiling translation to javascript');
        return gulp.src(config.translationfolder + '/**/*.po')
            .pipe($.if(args.verbose, $.print()))
            .pipe($.angularGettext.compile({
                module: 'fmuClientApp',
                // Let not do this, our translations are not big enough
                // format: 'json'
            }))
            .pipe(gulp.dest(config.translationfolder));
    });

    gulp.task('wiredep', ['sass', 'templatecache'], function() {
        log('Generate and wire .js/css dependencies to index.html');
        var wiredep = require('wiredep').stream;
        var options = config.getWiredepOptions();
        gulp.src(config.index)
            .pipe(wiredep(options))
            .pipe($.inject(gulp.src(config.jsfiles, {
                read: false
            }), {
                relative: true
            }))
            .pipe($.inject(gulp.src(config.cssfiles, {
                read: false
            }), {
                ignorePath: '.tmp',
                addRootSlash: false
            }))
            .pipe(gulp.dest(config.appPath));
    });

    gulp.task('connect-dev', ['wiredep'], function() {
        var serveStatic = require('serve-static');
        var serveIndex = require('serve-index');
        var url = require('url');
        var proxy = require('proxy-middleware');

        var app = require('connect')()
            .use(serveStatic(config.tmp))
            .use(serveStatic(config.appPath))
            // paths to bower_components should be relative to the current file
            // e.g. in app/index.html you should use ../bower_components
            .use(config.bower, serveStatic('bower_components'))
            .use(serveIndex('app'))
            .use('/app', proxy(url.parse('http://www.feber.se'))); //TODO check this !

        require('http').createServer(app)
            .listen(9000)
            .on('listening', function() {
                console.log('Started connect web server on http://localhost:9000');
                startBrowserSync([config.jsfiles, config.htmlfiles, config.cssfiles]);
            });
    });

    gulp.task('connect-prod', ['default'], function() {
        var serveStatic = require('serve-static');
        var serveIndex = require('serve-index');
        var app = require('connect')()
            .use(serveStatic(config.dist))
            .use(serveIndex('dist'));

        require('http').createServer(app)
            .listen(9000)
            .on('listening', function() {
                console.log('Started connect web server on http://localhost:9000');
            });
    });

    gulp.task('karma', ['jshint'], function(done) {
        log('Starting karma unittests');
        gulp.src(config.karmaconfig.files)
            .pipe($.print());
        startTests(true /* singlerun */ , done);
    });

    function startTests(singlerun, done) {
        var karma = require('karma').server;
        karma.start({
            configFile: __dirname + '/src/test/javascript/karma.conf.js',
            singleRun: !!singlerun
        }, karmaCompleted);

        function karmaCompleted(karmaResult) {
            log('Karma completed !');
            if (karmaResult === 1) {
                done('Karma test failed with code: ' + karmaResult);
            } else {
                done();
            }
        }
    }

    gulp.task('serve', ['connect-dev'], function() {
        gulp.watch(config.sassfiles, ['sass']);
    });

    gulp.task('build', ['jshint', 'images', 'fonts', 'extras', 'html'], function() {});

    gulp.task('default', ['clean-all'], function() {
        gulp.start('build');
    });

    // Helper functions

    function log(message) {
        $.util.log(message);
    }

    function clean(path) {
        log('Cleaning: ' + $.util.colors.yellow(path));
        del.bind(null, path);
    }

    function startBrowserSync(files) {
        log('started browser-sync');
        var browsersync = require('browser-sync');
        if (browsersync.active) {
            return;
        }
        var options = {
            proxy: 'localhost:9000',
            files: files,
            ghostMode: {
                clicks: true,
                forms: true,
                scroll: true,
                location: false
            },
            injectChanges: true,
            logFileChanges: true,
            logLevel: 'debug',
            logPrefix: 'gulp-patterns',
            notify: true
        };
        browsersync(options);
    }
})();
