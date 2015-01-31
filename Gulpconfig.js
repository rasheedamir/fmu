'use strict';

module.exports = function() {
    var config = {
        jsfiles: [
            './src/main/webapp/**/*.js',
            '!./src/main/webapp/dependencies/**',
            '!**/translations.js'
        ],
        sassfiles: [
            './src/main/webapp/common/styles/sass/sass-main.scss'
        ],
        dest: './src/main/webapp/dist/css'
    };

    return config;
};
