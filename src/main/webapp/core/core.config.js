(function() {
    'use strict';

    var core = angular.module('fmu.core');
    core.config(configure);

    configure.$inject = ['$logProvider', 'fmuExceptionHandlerProvider', 'gettext'];
    //function configure(/*$logProvider, $routeProvider, routehelperConfigProvider, gettext, fmuExceptionHandlerProvider) {
    function configure($logProvider, fmuExceptionHandlerProvider, gettext) {        
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

        // // Configure the common route provider
        // routehelperConfigProvider.config.$routeProvider = $routeProvider;
        // routehelperConfigProvider.config.docTitle = gettext('fmu-route-title/Fmu: ');
        // var resolveAlways = { /* @ngInject */
        //     ready: function(dataservice) {
        //         return dataservice.ready();
        //     }
        // };
        // routehelperConfigProvider.config.resolveAlways = resolveAlways;

        // Configure the common exception handler
        fmuExceptionHandlerProvider.configure(config.appErrorPrefix);
    }
})();