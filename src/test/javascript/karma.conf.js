// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function (config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: '../../..',

        // testing framework to use (jasmine/mocha/qunit/...)
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            "src/main/webapp/bower_components/jquery/dist/jquery.js",
            "src/main/webapp/bower_components/angular/angular.js",
            "src/main/webapp/bower_components/bootstrap/dist/js/bootstrap.js",
            "src/main/webapp/bower_components/angular-resource/angular-resource.js",
            "src/main/webapp/bower_components/angular-cookies/angular-cookies.js",
            "src/main/webapp/bower_components/angular-sanitize/angular-sanitize.js",
            "src/main/webapp/bower_components/angular-animate/angular-animate.js",
            "src/main/webapp/bower_components/angular-touch/angular-touch.js",
            "src/main/webapp/bower_components/angular-ui-router/release/angular-ui-router.js",
            "src/main/webapp/bower_components/angular-mocks/angular-mocks.js",
            'src/main/webapp/bower_components/ng-table/ng-table.js',
            'src/main/webapp/bower_components/underscore/underscore.js',
            'src/main/webapp/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'src/main/webapp/bower_components/ngDialog/js/ngDialog.js',
            'src/main/webapp/scripts/*.js',
            'src/main/webapp/scripts/**/*.js',
            'src/test/javascript/**/!(karma.conf).js'
        ],

        // list of files / patterns to exclude
        exclude: [],

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
