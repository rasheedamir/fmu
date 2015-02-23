(function() {
    'use strict';

    var core = angular.module('fmu.core');
    core.config(configure);

    /*@ngInject*/
    function configure($logProvider, fmuExceptionHandlerProvider, routehelperConfigProvider, gettext, $urlRouterProvider, $stateProvider) {
        core.value('config', config);
        var config = {
            appErrorPrefix: '[FMU Error]: ',
            appTitle: gettext('fmu-title/Fördjupad medicinsk utredning'),
            version: gettext('fmu-version/0.0.1')
        };

        // turn debugging off/on (no info or warn)
        if ($logProvider.debugEnabled) {
            $logProvider.debugEnabled(true);
        }

        // Configure the common route provider
        routehelperConfigProvider.config.$urlRouterProvider = $urlRouterProvider;
        routehelperConfigProvider.config.$stateProvider = $stateProvider;
        routehelperConfigProvider.config.docTitle = gettext('fmu-route-title/Fmu');

        // Configure the common exception handler
        fmuExceptionHandlerProvider.configure(config.appErrorPrefix);
    }
    configure.$inject = ['$logProvider', 'fmuExceptionHandlerProvider', 'routehelperConfigProvider', 'gettext', '$urlRouterProvider', '$stateProvider'];
})();