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
                    [
                        /* inject:js */
                        'src/main/webapp/app-dev.module.js',
                        'src/main/webapp/app.module.js',
                        'src/main/webapp/eavrop-overview/EavropController.js',
                        'src/main/webapp/eavrop-overview/EavropService.js',
                        'src/main/webapp/eavrop-overview/RequestAcceptanceController.js',
                        'src/main/webapp/eavrop-overview/RequestAssignmentController.js',
                        'src/main/webapp/orders-overview/OverviewCtrl.js',
                        'src/main/webapp/widgets/FMU-errorField.js',
                        'src/main/webapp/widgets/Fmu-datepicker.js',
                        'src/main/webapp/widgets/Fmu-responsive-menu.js',
                        'src/main/webapp/widgets/Fmu-simple-table.js',
                        'src/main/webapp/widgets/Fmu-table.js',
                        'src/main/webapp/widgets/Fmu-utredning-table.js',
                        'src/main/webapp/widgets/HandlingarTableDir.js',
                        'src/main/webapp/widgets/datetime-service.js',
                        'src/main/webapp/common/authentication/AuthService.js',
                        'src/main/webapp/common/authentication/FakeRoleCtrl.js',
                        'src/main/webapp/common/authentication/RestrictedDirective.js',
                        'src/main/webapp/common/rest/Eavrops.js',
                        'src/main/webapp/common/rest/RestUrlBuilderService.js',
                        'src/main/webapp/common/rest/constants.js',
                        'src/main/webapp/common/router/router-module.js',
                        'src/main/webapp/common/templatecache/templatecache.module.js',
                        'src/main/webapp/common/translations/fmu.pot.js',
                        'src/main/webapp/common/translations/translations.js',
                        'src/main/webapp/eavrop-overview/compensations/CompensationController.js',
                        'src/main/webapp/eavrop-overview/investigation/InvestigationController.js',
                        'src/main/webapp/eavrop-overview/investigation/InvestigationService.js',
                        'src/main/webapp/eavrop-overview/order/AddDocumentController.js',
                        'src/main/webapp/orders-overview/completed/CompletedController.js',
                        'src/main/webapp/orders-overview/incoming/IncomingController.js',
                        'src/main/webapp/orders-overview/ongoing/OngoingController.js',
                        /* endinject */
                    ],
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
                //ignorePath: config.bower.ignorePath
            };
            return options;
        };

        return config;
    };
})();
