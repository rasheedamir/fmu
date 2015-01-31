'use strict';

var config = require('./Gulpconfig')();
var gulp = require('gulp');
var args = require('yargs').argv;
var $ = require('gulp-load-plugins')({
    lazy: true
});


gulp.task('jshint', function() {
    return gulp.src(config.jsfiles)
        .pipe($.if(args.verbose, $.print()))
        .pipe($.jshint())
        .pipe($.jshint.reporter('jshint-stylish', {
            verbose: true
        }))
        .pipe($.jshint.reporter('fail'));
});

gulp.task('sass', [], function() {
    gulp.src(config.sassfiles)
        .pipe($.if(args.verbose, $.print()))
        .pipe($.sass({
            includePaths: require('node-bourbon').includePaths
        }))
        .on('error', function(error) {
            console.error(error);
            this.emit('end');
        })
        .pipe(gulp.dest(config.dest));
});
