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
            cssfiles: ['.tmp/styles/*.css'],
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
                        'src/main/webapp/app-dev.module.js',
                        'src/main/webapp/app.module.js',
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
                        'src/main/webapp/core/rest/RestUrlBuilder.service.js',
                        'src/main/webapp/core/rest/rest.service.js',
                        'src/main/webapp/utilities/exception/exception.service.js',
                        'src/main/webapp/utilities/logger/logger.service.js',
                        'src/main/webapp/utilities/router/router.service.js',
                        'src/main/webapp/fmu.config.route.js',
                        'src/main/webapp/core/core.config.js',
                        'src/main/webapp/eavrop-overview/EavropController.js',
                        'src/main/webapp/eavrop-overview/EavropService.js',
                        'src/main/webapp/eavrop-overview/RequestAcceptanceController.js',
                        'src/main/webapp/eavrop-overview/RequestAssignmentController.js',
                        'src/main/webapp/eavrop-overview/eavrop.config.route.js',
                        'src/main/webapp/login/login.route.js',
                        'src/main/webapp/orders-overview/OverviewCtrl.js',
                        'src/main/webapp/widgets/FMU-errorField.js',
                        'src/main/webapp/widgets/Fmu-datepicker.js',
                        'src/main/webapp/widgets/Fmu-responsive-menu.js',
                        'src/main/webapp/widgets/Fmu-simple-table.js',
                        'src/main/webapp/widgets/Fmu-table.js',
                        'src/main/webapp/widgets/Fmu-utredning-table.js',
                        'src/main/webapp/widgets/HandlingarTableDir.js',
                        'src/main/webapp/widgets/datetime-service.js',
                        'src/main/webapp/core/authentication/AuthService.js',
                        'src/main/webapp/core/authentication/FakeRoleCtrl.js',
                        'src/main/webapp/core/authentication/RestrictedDirective.js',
                        'src/main/webapp/core/authentication/role.controller.js',
                        'src/main/webapp/core/rest/Eavrops.js',
                        'src/main/webapp/core/translations/fmu.pot.js',
                        'src/main/webapp/core/translations/translations.js',
                        'src/main/webapp/eavrop-overview/compensations/CompensationController.js',
                        'src/main/webapp/eavrop-overview/investigation/InvestigationController.js',
                        'src/main/webapp/eavrop-overview/investigation/InvestigationService.js',
                        'src/main/webapp/eavrop-overview/order/AddDocumentController.js',
                        'src/main/webapp/orders-overview/completed/CompletedController.js',
                        'src/main/webapp/orders-overview/incoming/IncomingController.js',
                        'src/main/webapp/orders-overview/ongoing/OngoingController.js',
                        'src/main/webapp/utilities/exception/exceptionHandler.provider.js',
                        'src/main/webapp/dependencies/bootstrap/js/bootstrap.js',
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
