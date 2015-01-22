// Generated on 2014-09-10 using generator-angular 0.9.8
'use strict';

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to recursively match all subfolders:
// 'test/spec/**/*.js'
var proxySnippet = require('grunt-connect-proxy/lib/utils').proxyRequest;
module.exports = function (grunt) {

  // Load grunt tasks automatically
  require('load-grunt-tasks')(grunt);

  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);

  // Configurable paths for the application
  var appConfig = {
    app: require('./bower.json').appPath,
    dist: './src/main/webapp/dist'
  };

  // Define the configuration for all the tasks
  grunt.initConfig({
    // Project settings
    yeoman: appConfig,

    // Watches files for changes and runs tasks based on the changed files
    watch: {
      bower: {
        files: ['bower.json'],
        tasks: ['wiredep']
      },
      js: {
        files: ['<%= yeoman.app %>/scripts/{,*/}*.js'],
        tasks: ['newer:jshint:all'],
        options: {
          livereload: '<%= connect.options.livereload %>'
        }
      },
      jsTest: {
        files: ['src/test/javascript/spec/{,*/}*.js'],
        tasks: ['newer:jshint:test', 'karma']
      },
      // styles: {
      //   files: ['<%= yeoman.app %>/styles/**/*.css'],
      //   tasks: ['newer:copy:styles', 'autoprefixer']
      // },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      livereload: {
        options: {
          livereload: '<%= connect.options.livereload %>'
        },
        files: [
          '<%= yeoman.app %>/**/*.html',
          '.tmp/styles/**/*.css',
          '<%= yeoman.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
        ]
      },
      sass: {
        files: ['<%= yeoman.app %>/styles/sass/**/*.{scss,sass}'],
        tasks: ['sass:server', 'autoprefixer']
      }
    },

    // The actual grunt server settings
    connect: {
      proxies: [
          {
              context: '/app',
              host: 'localhost',
              port: 8080,
              https: false,
              changeOrigin: false
          },
          {
              context: '/fake',
              host: 'localhost',
              port: 8080,
              https: false,
              changeOrigin: false
          }
      ],
      options: {
        port: 9000,
        // Change this to '0.0.0.0' to access the server from outside.
        hostname: 'localhost',
        livereload: 35729
      },
      livereload: {
            options: {
                open: true,
                base: [
                    '.tmp',
                    'src/main/webapp'
                ],
                middleware: function (connect) {
                    return [
                        connect.static('.tmp'),
                        proxySnippet,
                        connect.static(require('path').resolve('src/main/webapp'))
                    ];
                }
            }
      },
      test: {
        options: {
          port: 9001,
          middleware: function (connect) {
            return [
              connect.static('.tmp'),
              connect.static('test'),
              connect().use(
                '/bower_components',
                connect.static('./bower_components')
              ),
              connect.static(appConfig.app)
            ];
          }
        }
      },
      dist: {
        options: {
          open: true,
          base: '<%= yeoman.dist %>'
        }
      }
    },
    // Compiles Sass to CSS and generates necessary files if requested
    sass: {
      options: {
        loadPath: '<%= yeoman.app %>/bower_components'
      },
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/styles/sass',
          src: ['*.{scss,sass}'],
          dest: '.tmp/styles',
          ext: '.css'
        }]
      },
      server: {
        options: {
          style: 'expanded'
        },
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/styles/sass',
          src: ['*.{scss,sass}'],
          dest: '.tmp/styles',
          ext: '.css'
        }]
      }
    },

    // Make sure code styles are up to par and there are no obvious mistakes
    jshint: {
      options: {
        jshintrc: '.jshintrc',
        reporter: require('jshint-stylish')
      },
      all: {
        src: [
          'Gruntfile.js',
          '<%= yeoman.app %>/scripts/{,*/}*.js'
        ]
      },
      test: {
        options: {
          jshintrc: 'src/test/javascript/.jshintrc'
        },
        src: ['src/test/javascript/spec/{,*/}*.js']
      }
    },

    // Empties folders to start fresh
    clean: {
      dist: {
        files: [{
          dot: true,
          src: [
            '.tmp',
            '<%= yeoman.dist %>/{,*/}*',
            '!<%= yeoman.dist %>/.git*'
          ]
        }]
      },
      server: '.tmp'
    },
    // Add vendor prefixed styles
    autoprefixer: {
      options: {
        browsers: ['last 1 version']
      },
      dist: {
        files: [{
          expand: true,
          cwd: '.tmp/styles/',
          src: '{,*/}*.css',
          dest: '.tmp/styles/'
        }]
      }
    },

    // Automatically inject Bower components into the app
    wiredep: {
      app: {
        src: ['<%= yeoman.app %>/index.html'],
        ignorePath:  /\.\.\//
      },
      sass: {
        src: ['<%= yeoman.app %>/styles/sass/**/*.{scss,sass}'],
        ignorePath: /(\.\.\/){1,2}bower_components\//
      }
    },

    // Renames files for browser caching purposes
    filerev: {
      dist: {
        src: [
          '<%= yeoman.dist %>/scripts/{,*/}*.js',
          '<%= yeoman.dist %>/styles/{,*/}*.css',
          '<%= yeoman.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
          '<%= yeoman.dist %>/styles/fonts/*'
        ]
      }
    },

    // Reads HTML for usemin blocks to enable smart builds that automatically
    // concat, minify and revision files. Creates configurations in memory so
    // additional tasks can operate on them
    useminPrepare: {
      options: {
        dest: '<%= yeoman.dist %>'
      },
      html: '<%= yeoman.app %>/index.html'
    },

    // Performs rewrites based on filerev and the useminPrepare configuration
    usemin: {
      html: ['<%= yeoman.dist %>/**/*.html'],
      css: ['<%= yeoman.dist %>/styles/{,*/}*.css'],
      options: {
        assetsDirs: [
        '<%= yeoman.dist %>',
        '<%= yeoman.dist %>/images',
        '<%= yeoman.dist %>/styles'
        ]
      }
    },

    // The following *-min tasks will produce minified files in the dist folder
    // By default, your `index.html`'s <!-- Usemin block --> will take care of
    // minification. These next options are pre-configured if you do not wish
    // to use the Usemin blocks.
    // cssmin: {
    //   dist: {
    //     files: {
    //       '<%= yeoman.dist %>/styles/main.css': [
    //         '.tmp/styles/{,*/}*.css'
    //       ]
    //     }
    //   }
    // },
    // uglify: {
    //   dist: {
    //     files: {
    //       '<%= yeoman.dist %>/scripts/scripts.js': [
    //         '<%= yeoman.dist %>/scripts/scripts.js'
    //       ]
    //     }
    //   }
    // },
    // concat: {
    //   dist: {}
    // },

    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/images',
          src: '{,*/}*.{png,jpg,jpeg,gif}',
          dest: '<%= yeoman.dist %>/images'
        }]
      }
    },

    svgmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/images',
          src: '{,*/}*.svg',
          dest: '<%= yeoman.dist %>/images'
        }]
      }
    },

    htmlmin: {
      dist: {
        options: {
          collapseWhitespace: true,
          conservativeCollapse: true,
          collapseBooleanAttributes: true,
          removeCommentsFromCDATA: true,
          removeOptionalTags: true
        },
        files: [{
          expand: true,
          cwd: '<%= yeoman.dist %>',
          src: ['*.html', 'views/**/*.html'],
          dest: '<%= yeoman.dist %>'
        }]
      }
    },

    // ng-annotate tries to make the code safe for minification automatically
    // by using the Angular long form for dependency injection.
    ngAnnotate: {
      dist: {
        files: [{
          expand: true,
          cwd: '.tmp/concat/scripts',
          src: ['*.js', '!oldieshim.js'],
          dest: '.tmp/concat/scripts'
        }]
      }
    },

    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= yeoman.app %>',
          dest: '<%= yeoman.dist %>',
          src: [
            '*.{ico,png,txt}',
            '.htaccess',
            '*.html',
            'views/**/*.html',
            'images/{,*/}*.{webp}',
            'fonts/*'
          ]
        }, {
          expand: true,
          cwd: '.tmp/images',
          dest: '<%= yeoman.dist %>/images',
          src: ['generated/*']
        }, {
          expand: true,
          cwd: '<%= yeoman.app %>/bower_components/bootstrap/dist',
          src: 'fonts/*',
          dest: '<%= yeoman.dist %>'
        }]
      },
      styles: {
        expand: true,
        cwd: '<%= yeoman.app %>/styles/css',
        dest: '.tmp/styles/',
        src: '{,*/}*.css'
      }
    },

    // Run some tasks in parallel to speed up the build process
    concurrent: {
      server: [
        'sass:server',
        'copy:styles'
      ],
      test: [
        'copy:styles'
      ],
      dist: [
        'sass',
        'copy:styles',
        'imagemin',
        'svgmin'
      ]
    },

    // Test settings
    karma: {
      unit: {
        configFile: 'src/test/javascript/karma.conf.js',
        singleRun: true
      }
    },

    // Extract texts from html files for translation or override
       nggettext_extract: {
      pot: {
        files: {
          /* fileblock:html partials */
          	'<%= yeoman.app %>/texts/404.pot':['<%= yeoman.app %>/404.html'],
          	'<%= yeoman.app %>/texts/index.pot':['<%= yeoman.app %>/index.html'],
          	'<%= yeoman.app %>/texts/accept-request-modal.pot':['<%= yeoman.app %>/accept-request-modal.html'],
          	'<%= yeoman.app %>/texts/add-note-modal.pot':['<%= yeoman.app %>/add-note-modal.html'],
          	'<%= yeoman.app %>/texts/all-events.pot':['<%= yeoman.app %>/all-events.html'],
          	'<%= yeoman.app %>/texts/assign-utredare-modal.pot':['<%= yeoman.app %>/assign-utredare-modal.html'],
          	'<%= yeoman.app %>/texts/compensation.pot':['<%= yeoman.app %>/compensation.html'],
          	'<%= yeoman.app %>/texts/confirmModal.pot':['<%= yeoman.app %>/confirmModal.html'],
          	'<%= yeoman.app %>/texts/eavrop.pot':['<%= yeoman.app %>/eavrop.html'],
          	'<%= yeoman.app %>/texts/investigation.pot':['<%= yeoman.app %>/investigation.html'],
          	'<%= yeoman.app %>/texts/notes.pot':['<%= yeoman.app %>/notes.html'],
          	'<%= yeoman.app %>/texts/add-doc-modal.pot':['<%= yeoman.app %>/add-doc-modal.html'],
          	'<%= yeoman.app %>/texts/citizen.pot':['<%= yeoman.app %>/citizen.html'],
          	'<%= yeoman.app %>/texts/contents.pot':['<%= yeoman.app %>/contents.html'],
          	'<%= yeoman.app %>/texts/documents.pot':['<%= yeoman.app %>/documents.html'],
          	'<%= yeoman.app %>/texts/order.pot':['<%= yeoman.app %>/order.html'],
          	'<%= yeoman.app %>/texts/req-amendment-modal.pot':['<%= yeoman.app %>/req-amendment-modal.html'],
          	'<%= yeoman.app %>/texts/reject-request-modal.pot':['<%= yeoman.app %>/reject-request-modal.html'],
          	'<%= yeoman.app %>/texts/completed.pot':['<%= yeoman.app %>/completed.html'],
          	'<%= yeoman.app %>/texts/ongoing.pot':['<%= yeoman.app %>/ongoing.html'],
          	'<%= yeoman.app %>/texts/orders.pot':['<%= yeoman.app %>/orders.html'],
          	'<%= yeoman.app %>/texts/overview.pot':['<%= yeoman.app %>/overview.html'],
          	'<%= yeoman.app %>/texts/changeBookingConfirmationModal.pot':['<%= yeoman.app %>/changeBookingConfirmationModal.html'],
          	'<%= yeoman.app %>/texts/datePicker.pot':['<%= yeoman.app %>/datePicker.html'],
          	'<%= yeoman.app %>/texts/fmu-simple-table.pot':['<%= yeoman.app %>/fmu-simple-table.html'],
          	'<%= yeoman.app %>/texts/fmu-table.pot':['<%= yeoman.app %>/fmu-table.html'],
          	'<%= yeoman.app %>/texts/fmu-utredning-table.pot':['<%= yeoman.app %>/fmu-utredning-table.html'],
          	'<%= yeoman.app %>/texts/laggTillHandelseDialog.pot':['<%= yeoman.app %>/laggTillHandelseDialog.html'],
          	'<%= yeoman.app %>/texts/welcome.pot':['<%= yeoman.app %>/welcome.html'],
          /* endfileblock */
        }
      }
    },
    fileblocks: {
      options: {
        /* Task options */
        removeFiles: true,
        templates: {
            html: '${file}'
        },
        templatesFn: {
            html: function(file){
                var path = require('path');
                var fileExtension = path.extname(file);
                var filename = path.basename(file, fileExtension);
                return '\t' + '\'<%= yeoman.app %>/texts/' + filename + '.pot\'' + ':[\'<%= yeoman.app %>/' + filename + '.html\'],';
            }
        }
      },
      htmls: {
        files: [
          {
            src: 'Gruntfile.js',
            blocks: {
                partials : {
                    cwd: '<%= yeoman.app %>',
                    src: ['**/*.html', '!bower_components/**']
                }
            }
          }
        ]
      }
    },
    // Inject any dependencies to a file when ordering is not important
    injector: {
        options: {
          starttag: '/* inject:{{ext}} */',
          endtag: '/* inject-end */',
          transform: function(filepath) {
            var path = require('path');
            var fileExtension = path.extname(filepath);
            var filename = path.basename(filepath, fileExtension);
            if(fileExtension === '.html'){
              return '\t' + '\'<%= yeoman.app %>/texts/' + filename + '.pot\'' + ':[\'<%= yeoman.app %>/' + filename + 'index.html\'],';
            }
          }
        },
        textextraction: {
            files: {
                '<%= yeoman.app %>/texts/index.js': ['<%= yeoman.app %>/**/*.html'],
            }
        }
    }
  });
  grunt.registerTask('serve', 'Compile then start a connect web server', function (target) {
    if (target === 'dist') {
      return grunt.task.run(['build', 'connect:dist:keepalive']);
    }

    grunt.task.run([
      'clean:server',
      'wiredep',
      'concurrent:server',
      'autoprefixer',
      'configureProxies',
      'connect:livereload',
      'watch'
    ]);
  });

  grunt.registerTask('server', 'DEPRECATED TASK. Use the "serve" task instead', function (target) {
    grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
    grunt.task.run(['serve:' + target]);
  });

  grunt.registerTask('test', [
    'clean:server',
    'concurrent:test',
    'autoprefixer',
    'connect:test',
    'karma'
  ]);

  grunt.registerTask('build', [
    'clean:dist',
    'wiredep',
    'useminPrepare',
    'concurrent:dist',
    'autoprefixer',
    'concat',
    'ngAnnotate',
    'copy:dist',
    'cssmin',
    'uglify',
    'filerev',
    'usemin',
    'htmlmin'
  ]);

  grunt.registerTask('default', [
    'newer:jshint',
    'test',
    'build'
  ]);
};
