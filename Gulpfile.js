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

    gulp.task('clean-all', ['clean-template'], del.bind(null, [config.tmp, config.dist]));

    gulp.task('sass', ['clean-styles'], function() {
        log('Processing and compiling SCSS to CSS');
        return gulp.src(config.sassfiles)
            .pipe($.plumber())
            .pipe($.if(args.verbose, $.print()))
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
        files.push('!' + config.appPath + '/core/translations/**');
        files.push('!' + config.appPath + '/core/templatecache/**');
        files.push('!' + config.appPath + '/dependencies/**');

        return gulp.src(files)
            .pipe($.plumber())
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
            .pipe(gulp.dest(config.dist + '/images'));
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
            .pipe($.plumber())
            .pipe($.if(args.verbose, $.print()))
            .pipe(gulp.dest(config.dist));
    });

    gulp.task('extract-pot', function() {
        log('Extract texts from html/js files');
        return gulp.src(config.translationfiles)
            .pipe($.plumber())
            .pipe($.if(args.verbose, $.print()))
            .pipe($.angularGettext.extract('translation.pot', {}))
            .pipe(gulp.dest(config.translationfolder));
    });

    gulp.task('compile-po', function() {
        log('Compiling translation to javascript');
        return gulp.src(config.translationfolder + '/**/*.po')
            .pipe($.plumber())
            .pipe($.if(args.verbose, $.print()))
            .pipe($.angularGettext.compile({
                module: 'fmu.core'
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
            }).pipe($.print()), {
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

    gulp.task('karma-inject', ['templatecache'], function() {
        log('Inject app javascript source files to karmaconfig options');
        gulp.src('./Gulpconfig.js')
            .pipe($.inject(
                gulp.src(config.jsfiles, {
                    read: false
                }), {
                    starttag: '/* inject:js */',
                    endtag: '/* endinject */',
                    transform: function(filepath) {
                        var retval = '\'' + filepath.slice(1) + '\',';
                        return retval;
                    }
                }))
            .pipe(gulp.dest('.'));
    });

    gulp.task('test', ['jshint', 'karma-inject'], function(done) {
        log('Starting karma unittests');
        startTests(true /* singlerun */ , done);
    });

    gulp.task('tdd', ['karma-inject'], function(done) {
        log('Starting karma unittests');
        startTests(false /* singlerun */ , done);
    });

    function startTests(singlerun, done) {
        var karma = require('karma').server;

        function karmaCompleted(karmaResult) {
            log('Karma completed !');
            if (karmaResult === 1) {
                done('Karma test failed with code: ' + karmaResult);
            } else {
                done();
            }
        }

        karma.start({
            configFile: __dirname + '/src/test/javascript/karma.conf.js',
            singleRun: !!singlerun
        }, karmaCompleted);
    }

    gulp.task('serve',['wiredep'], function() {
        startBrowserSync([config.jsfiles, config.htmlfiles, config.cssfiles]);
        gulp.watch(config.sassfiles, ['sass']);
    });

    gulp.task('build', ['jshint', 'images', 'fonts', 'extras', 'html'], function() {});

    gulp.task('default', ['clean-all'], function() {
        gulp.start('build');
    });

    // Helper functions

    function log(message) {
        $.util.log('------> ' + message);
    }

    function clean(path) {
        log('Cleaning: ' + $.util.colors.yellow(path));
        del.bind(null, path);
    }

    function startBrowserSync(files) {
        log('started browser-sync');
        var url = require('url');
        var proxy = require('proxy-middleware');
        var browsersync = require('browser-sync');

        var proxyFake = url.parse('http://ec2-54-154-123-186.eu-west-1.compute.amazonaws.com:9000/fake');
        proxyFake.route = '/fake';

        var proxyRest = url.parse('http://ec2-54-154-123-186.eu-west-1.compute.amazonaws.com:9000/app/');
        proxyRest.route = '/app/';


        if (browsersync.active) {
            return;
        }
        var options = {
            port: 9000,
            server: {
                baseDir: [config.appPath, config.tmp, '!' + config.dist],
                //directory: true,
                middleware: [proxy(proxyFake), proxy(proxyRest)]
            },
            files: files,
            watchOptions: {
                debounceDelay: 1000
            },
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