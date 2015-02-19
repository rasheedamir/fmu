(function() {
    'use strict';

    angular.module('fmu.overview')
        .run(setUpRoutes);

    setUpRoutes.$inject = ['routeHelper', 'gettext', 'Dataservice', 'logger'];

    function setUpRoutes(routeHelper, gettext) {
        var states = [{
            stateName: 'overview',
            stateConfig: {
                url: '/overview',
                abstract: true,
                controller: 'OverviewController',
                templateUrl: 'orders-overview/overview.html'
            }
        }, {
            stateName: 'overview.orders',
            stateConfig: {
                url: '/orders',
                title: gettext('Overview-order-title/Orders'),
                templateUrl: 'orders-overview/incoming/incoming.html',
                controller: 'OrderController'
            }
        }, {
            stateName: 'overview.ongoing',
            stateConfig: {
                url: '/ongoing',
                title: gettext('Overview-ongoing-title/Ongoing'),
                templateUrl: 'orders-overview/ongoing/ongoing.html',
                controller: 'OngoingController'
            }
        }, {
            stateName: 'overview.completed',
            stateConfig: {
                url: '/completed',
                title: gettext('Overview-completed-title/Completed'),
                templateUrl: 'orders-overview/completed/completed.html',
                controller: 'CompletedController'
            }
        }];

        routeHelper.registerStates(states);
    }
})();