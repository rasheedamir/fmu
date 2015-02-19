(function() {
    'use strict';

    angular.module('fmu.login')
        .run(setUpRoutes);

    setUpRoutes.$inject = ['routeHelper', 'gettext'];

    function setUpRoutes(routeHelper, gettext) {
        var stateName = 'login';
        var stateConfig = {
            url: '/login',
            templateUrl: 'login/login.html',
            title: gettext('login-title/Login'),
            controller: 'LoginController'
        };

        routeHelper.registerState(stateName, stateConfig);
    }
})();