(function() {
    'use strict';

    module.exports = function() {
        var appPath = './src/main/webapp';
        var dist = appPath + '/dist';
        var bower = appPath + '/dependencies/bower_components/';
        var translationfolder = appPath + '/common/translations';
        var unittest = './src/test/javascript';

        var config = {
            appPath: appPath,
            tmp: './.tmp',
            index: appPath + '/index.html',
            jsfiles: [
                appPath + '/**/*.js',
                '!./src/main/webapp/dependencies/**',
                '!' + dist + '/**'
            ],
            sassfiles: [
                appPath + '/common/styles/sass/**/*.scss'
            ],
            cssfiles: ['.tmp/styles/*.css'],
            htmlfiles: appPath + '**/**.html',
            translationfolder: translationfolder,
            translationfiles: [appPath + '/**/**.{html,js}',
                '!' + appPath + '/dependencies/**',
                '!' + appPath + '/dist/**'
            ],
            imagefiles: appPath + '/common/images/**/*.{png,jpg,jpeg,gif}',
            fonts: [appPath + '/dependencies/**/*.{eot,svg,ttf,woff}', appPath + '/fonts/**/*.{eot,svg,ttf,woff}'],
            templatecache: appPath + '/common/templatecache',
            dist: dist,
            bower: {
                json: require('./bower.json'),
                directory: bower
            },
            unittestFolder: unittest,
            karmaconfig: getKarmaConfigOptions()
        };

        function getKarmaConfigOptions() {
            var bowerfiles = require('wiredep')({
                devDependencies: true
            }).js;

            var options = {
                files: [].concat(
                    bowerfiles,
                    appPath + '**/**/*.module.js',
                    appPath + '**/**/*.js',
                    unittest + '/spec/**/*.js'
                ),
                exclude: [appPath + '/dist/**'],
                coverage: {
                    dir: unittest + '/coverage',
                    reporters: [{
                        type: 'html',
                        subdir: 'report-html'
                    }, {
                        type: 'lcov',
                        subdir: 'report-lcov'
                    }, {
                        type: 'text-summary'
                    }],
                    preprocessors: [unittest + '**/!(*.spec) + (.js)']
                }
            };

            return options;
        }

        config.getWiredepOptions = function() {
            var options = {
                bowerJson: config.bower.json,
                directory: config.bower.directory,
                ignorePath: config.bower.ignorePath
            };
            return options;
        };

        return config;
    };
})();
