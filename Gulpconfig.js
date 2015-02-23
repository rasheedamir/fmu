(function() {
    'use strict';

    module.exports = function() {
        var appPath = './src/main/webapp';
        var dist = appPath + '/dist';
        var bower = appPath + '/dependencies/bower_components/';
        var translationfolder = appPath + '/core/translations';
        var unittest = './src/test/javascript';

        var config = {
            appPath: appPath,
            tmp: './.tmp',
            index: appPath + '/index.html',
            jsfiles: [
                appPath + '/**/*.module.js',
                appPath + '/**/*.constant.js',
                appPath + '/**/*.service.js',
                appPath + '/**/*.directive.js',
                appPath + '/**/*.js',
                '!./src/main/webapp/dependencies/bower_components/**',
                '!' + dist + '/**'
            ],
            sassfiles: [
                appPath + '/styles/sass/**/*.scss'
            ],
            cssPath: appPath + '/styles/css',
            sassPath: appPath + '/styles/sass',
            cssfiles: [appPath + '/styles/css/*.css'],
            htmlfiles: appPath + '**/**.html',
            translationfolder: translationfolder,
            translationfiles: [appPath + '/**/**.{html,js}',
                '!' + appPath + '/dependencies/**',
                '!' + appPath + '/dist/**'
            ],
            imagefiles: appPath + '/images/**/*.{png,jpg,jpeg,gif}',
            fonts: [appPath + '/dependencies/**/*.{eot,svg,ttf,woff}', appPath + '/fonts/**/*.{eot,svg,ttf,woff}'],
            templatecache: appPath + '/core/templatecache',
            dist: dist,
            bower: {
                json: require('./bower.json'),
                directory: bower
            },
            backendServerUrl: 'http://ec2-54-154-123-186.eu-west-1.compute.amazonaws.com:9000',
            unittestFolder: unittest,
            mockRestData: unittest + '/mocks/db.json',
            karmaconfig: getKarmaConfigOptions()
        };

        function getKarmaConfigOptions() {
            var bowerfiles = require('wiredep')({
                devDependencies: true
            }).js;

            var options = {
                files: [].concat(
                    bowerfiles,
                    [
                        /* inject:js */
                        'src/main/webapp/fmu.module.js',
                        'src/main/webapp/core/core.module.js',
                        'src/main/webapp/eavrop-overview/eavrop.module.js',
                        'src/main/webapp/login/login.module.js',
                        'src/main/webapp/orders-overview/overview.module.js',
                        'src/main/webapp/widgets/widgets.module.js',
                        'src/main/webapp/core/rest/rest.module.js',
                        'src/main/webapp/core/templatecache/templatecache.module.js',
                        'src/main/webapp/utilities/exception/exception.module.js',
                        'src/main/webapp/utilities/logger/logger.module.js',
                        'src/main/webapp/utilities/router/router.module.js',
                        'src/main/webapp/eavrop-overview/eavrop.constant.js',
                        'src/main/webapp/core/authentication/auth.constant.js',
                        'src/main/webapp/core/rest/rest.constant.js',
                        'src/main/webapp/core/authentication/auth.service.js',
                        'src/main/webapp/core/rest/rest.service.js',
                        'src/main/webapp/core/rest/restUrlBuilder.service.js',
                        'src/main/webapp/eavrop-overview/investigation/investigation.service.js',
                        'src/main/webapp/utilities/exception/exception.service.js',
                        'src/main/webapp/utilities/logger/logger.service.js',
                        'src/main/webapp/utilities/router/router.service.js',
                        'src/main/webapp/core/authentication/restricted.directive.js',
                        'src/main/webapp/fmu.config.route.js',
                        'src/main/webapp/core/core.config.js',
                        'src/main/webapp/eavrop-overview/eavrop.config.route.js',
                        'src/main/webapp/eavrop-overview/eavrop.controller.js',
                        'src/main/webapp/eavrop-overview/eavropService.js',
                        'src/main/webapp/eavrop-overview/requestAcceptance.controller.js',
                        'src/main/webapp/eavrop-overview/requestAssignment.controller.js',
                        'src/main/webapp/login/login.route.js',
                        'src/main/webapp/orders-overview/overview.config.route.js',
                        'src/main/webapp/orders-overview/overview.controller.js',
                        'src/main/webapp/widgets/FMU-errorField.js',
                        'src/main/webapp/widgets/Fmu-datepicker.js',
                        'src/main/webapp/widgets/Fmu-responsive-menu.js',
                        'src/main/webapp/widgets/Fmu-simple-table.js',
                        'src/main/webapp/widgets/Fmu-table.js',
                        'src/main/webapp/widgets/Fmu-utredning-table.js',
                        'src/main/webapp/widgets/HandlingarTableDir.js',
                        'src/main/webapp/widgets/datetime-service.js',
                        'src/main/webapp/core/authentication/fakeRole.controller.js',
                        'src/main/webapp/core/translations/translation.pot.js',
                        'src/main/webapp/eavrop-overview/allEvents/allEvents.controller.js',
                        'src/main/webapp/eavrop-overview/compensations/compensation.controller.js',
                        'src/main/webapp/eavrop-overview/investigation/Investigation.controller.js',
                        'src/main/webapp/eavrop-overview/investigation/addBookingModal.controller.js',
                        'src/main/webapp/eavrop-overview/notes/notes.controller.js',
                        'src/main/webapp/orders-overview/completed/completed.controller.js',
                        'src/main/webapp/orders-overview/incoming/incoming.controller.js',
                        'src/main/webapp/orders-overview/ongoing/ongoing.controller.js',
                        'src/main/webapp/utilities/exception/exceptionHandler.provider.js',
                        'src/main/webapp/dependencies/bootstrap/js/bootstrap.js',
                        'src/main/webapp/eavrop-overview/order/documents/addDocModal.controller.js',
                        'src/main/webapp/eavrop-overview/order/documents/addDocument.controller.js',
                        'src/main/webapp/eavrop-overview/order/documents/documents.controller.js',
                        'src/main/webapp/eavrop-overview/order/documents/reqAmendmentModal.controller.js',
                        /* endinject */
                    ],
                    unittest + '/util/**/*.util.js',
                    unittest + '/**/**.spec.js'
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
                    }]
                },
                preprocessors: {}
            };

            options.preprocessors['../../../src/main/webapp/**/*.module.js'] = 'coverage';
            return options;
        }

        config.getWiredepOptions = function() {
            var options = {
                bowerJson: config.bower.json,
                directory: config.bower.directory,
                //ignorePath: config.bower.ignorePath
            };
            return options;
        };

        return config;
    };
})();
