(function() {
    'use strict';
    angular.module('util.router')
        .provider('routehelperConfig', routehelperConfig)
        .factory('routeHelper', routeHelper);

    function routehelperConfig() {
        /* jshint validthis:true */
        this.config = {
            // These are the properties we need to set
            // $urlRouterProvider: undefined
            // $stateProvider: undefined
            // defaultState: '/'
            // docTitle: ''
        };

        this.$get = function() {
            return {
                config: this.config
            };
        };
    }

    routeHelper.$inject = ['$rootScope', 'logger', 'routehelperConfig'];

    function routeHelper($rootScope, logger, routehelperConfig) {
        var service = {
            registerState: registerState,
            setDefaultState: setDefaultState
        };

        init();
        return service;


        function registerState(stateName, stateConfig) {
            routehelperConfig.config.$stateProvider
                .state(stateName, stateConfig);
        }

        function setDefaultState(statePath) {
            routehelperConfig.config.defaultState = statePath || '/';
            routehelperConfig.config.$urlRouterProvider.otherwise('' + routehelperConfig.config.defaultState);
        }

        function init() {
            handleErrors();
            updateTitle();
        }

        function handleErrors() {
            $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams) {
                console.log('Failed to transition from ' + fromState +
                    ' with parameters ' + fromParams + ' to ' + toState + ' with params ' + toParams);
            });
        }

        function updateTitle() {
            $rootScope.$on('$stateChangeSuccess',
                function(event, toState) {
                    var title = (toState.title || 'Fmu');
                    $rootScope.title = title;
                });
        }
    }

})();