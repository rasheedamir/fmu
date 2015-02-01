'use strict';

module.exports = function() {
    var appPath = './src/main/webapp';
    var dist = appPath + '/dist';
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
        dist: dist,
        bower: {
            json: require('./bower.json'),
            directory: appPath + '/dependencies/bower_components/'
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
