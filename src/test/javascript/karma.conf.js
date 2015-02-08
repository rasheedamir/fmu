(function() {
    'use strict';
    // Karma configuration
    // http://karma-runner.github.io/0.10/config/configuration-file.html

    module.exports = function(config) {
        var gulpconf = require('../../../Gulpconfig')();
        config.set({
            // base path, that will be used to resolve files and exclude
            basePath: '../../../',

            // testing framework to use (jasmine/mocha/qunit/...)
            frameworks: ['jasmine'],

            // list of files / patterns to load in the browser
            files: gulpconf.karmaconfig.files,
            // list of files / patterns to exclude
            exclude: gulpconf.karmaconfig.exclude,

            preprocesssors: gulpconf.karmaconfig.preprocesssors,

            reporters: ['progress', 'coverage'],

            coverageReporter: {
                dir: gulpconf.karmaconfig.coverage.dir,
                reporters: gulpconf.karmaconfig.coverage.reporters
            },

            colors: true,

            // web server port
            port: 9876,

            // level of logging
            // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
            logLevel: config.LOG_INFO,

            // enable / disable watching file and executing tests whenever any file changes
            autoWatch: false,

            // Start these browsers, currently available:
            // - Chrome
            // - ChromeCanary
            // - Firefox
            // - Opera
            // - Safari (only Mac)
            // - PhantomJS
            // - IE (only Windows)
            browsers: ['PhantomJS'],

            // Continuous Integration mode
            // if true, it capture browsers, run tests and exit
            singleRun: false
        });
    };
})();

