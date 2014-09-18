'use strict';

/**
 * @ngdoc overview
 * @name fmuClientApp
 * @description
 * # fmuClientApp
 *
 * Main module of the application.
 */
angular
.module('fmuClientApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'ui.bootstrap'
])
.run(['$rootScope', '$state', function($rootScope, $state){
    $rootScope.$state = $state;
}])
.config(function($stateProvider, $urlRouterProvider) {
    //
    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise('/overview/orders');
    //
    // Now set up the states
    $stateProvider
    .state('overview', {
        url: '/overview',
        abstract: true,
        controller: 'OverviewCtrl',
        templateUrl: 'views/overview/overview.html',
    })
    .state('overview.orders', {
        url: '/orders',
        templateUrl: 'views/overview/orders.html',
    })
    .state('overview.ongoing', {
        url: '/ongoing',
        templateUrl: 'views/overview/ongoing.html',
    })
    .state('overview.completed', {
        url: '/completed',
        templateUrl: 'views/overview/completed.html',
    })
    .state('eavrop', {
        url: '/eavrop/{eavropId:[0-9]+}',
        abstract: true,
        resolve: {
            currentEavrop: function($stateParams, Eavrop){
                return Eavrop.get({eavropId: $stateParams.eavropId});
            }
        },
        controller: 'EavropCtrl',
        templateUrl: 'views/eavrop/eavrop.html',
    })
    .state('eavrop.order', {
        url: '/order',
        templateUrl: 'views/eavrop/order.html',
    })
    .state('eavrop.allevents', {
        url: '/all-events',
        templateUrl: 'views/eavrop/all-events.html',
    })
    .state('eavrop.compensation', {
        url: '/compensation',
        templateUrl: 'views/eavrop/compensation.html',
    })
    .state('eavrop.investigation', {
        url: '/investigation',
        templateUrl: 'views/eavrop/investigation.html',
    })
    .state('eavrop.notes', {
        url: '/notes',
        templateUrl: 'views/eavrop/notes.html',
    });
});
