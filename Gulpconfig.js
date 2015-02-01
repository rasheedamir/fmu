'use strict';

module.exports = function() {
    var appPath = './src/main/webapp';
    var dist = appPath + '/dist';
    var bower = appPath + '/dependencies/bower_components/';
    var config = {
        appPath: appPath,
        tmp: './.tmp',
        index: appPath + '/index.html',
        jsfiles: [
            appPath + '/**/*.js',
            '!./src/main/webapp/dependencies/**',
            '!**/translations.js',
            '!' + dist + '/**/*.js'
        ],
        sassfiles: [
            appPath + '/common/styles/sass/**/*.scss'
        ],
        cssfiles: ['.tmp/styles/*.css'],
        imagefiles: appPath + '/common/images/**/*.{png,jpg,jpeg,gif}',
        fonts: [bower + '**/*.{eot,svg,ttf,woff}', appPath + '/fonts/**'],
        dist: dist,
        bower: {
            json: require('./bower.json'),
            directory: bower
        }
    };

    config.getWiredepOptions = function(){
        var options = {
            bowerJson: config.bower.json,
            directory: config.bower.directory,
            ignorePath: config.bower.ignorePath
        };
        return options;
    };

    return config;
};
