'use strict';

/**
 * @ngdoc overview
 * @name fmuClientApp
 * @description
 * # fmuClientApp
 *
 * Main module of the application.
 */
angular.module('fmuClientApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'ngTable',
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
        templateUrl: 'views/overview/overview.html'
    })
    .state('overview.orders', {
        url: '/orders',
        templateUrl: 'views/overview/orders.html',
        controller: 'OrderController'
    })
    .state('overview.ongoing', {
        url: '/ongoing',
        templateUrl: 'views/overview/ongoing.html',
        controller: 'OngoingController'
    })
    .state('overview.completed', {
        url: '/completed',
        templateUrl: 'views/overview/completed.html',
        controller: 'CompletedController'
    })
    .state('eavrop', {
        url: '/eavrop/{eavropId:[0-9]+}',
        abstract: true,
        resolve: {
            currentEavrop: function($stateParams, Eavrops){
                return Eavrops.get({eavropId: $stateParams.eavropId});
            }
        },
        controller: 'EavropCtrl',
        templateUrl: 'views/eavrop/eavrop.html',
    })
    .state('eavrop.order', {
        url: '/order',
        templateUrl: 'views/eavrop/order/order.html',
        abstract: true,
    })
    .state('eavrop.order.contents', {
        url: '/contents',
        templateUrl: 'views/eavrop/order/contents.html',
    })
    .state('eavrop.order.documents', {
        url: '/documents',
        templateUrl: 'views/eavrop/order/documents.html',
        resolve: {
            Documents: function(EavropDocuments){
                return EavropDocuments;
            },
            ReqDocuments: function(EavropRequestedDocuments){
                return EavropRequestedDocuments;
            }
        },
        controller: function($scope, Documents, ReqDocuments, $modal, $stateParams){

            function loadDocuments(){
                $scope.documents = Documents.query({eavropId: $stateParams.eavropId});
            }

            function loadReqDocuments(){
                $scope.reqDocuments = ReqDocuments.query({eavropId: $stateParams.eavropId});
            }

            loadDocuments();
            loadReqDocuments();

            var addDocModalCtrl = ['$scope','$modalInstance',  function($scope, $modalInstance){

                $scope.doc = {};
                $scope.picker = {opened: false};
                $scope.open = function($event){
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.picker.opened = true;
                };

                $scope.save = function(){
                    $modalInstance.close($scope.doc);
                };
                $scope.close = function(){$modalInstance.dismiss();};
            }];
            var reqAmendmentModalCtrl = ['$scope','$modalInstance',  function($scope, $modalInstance){

                $scope.doc = {};
                $scope.save = function(){
                    $modalInstance.close($scope.doc);
                };
                $scope.close = function(){$modalInstance.dismiss();};
            }];

            $scope.openReqAmendmentModal = function(){
                var mod = $modal.open({
                    templateUrl: 'views/eavrop/order/req-amendment-modal.html',
                    size: 'md',
                    controller: reqAmendmentModalCtrl,
                });

                mod.result.then(function(result){
                    new ReqDocuments(result).$save({eavropId: $stateParams.eavropId});
                    loadReqDocuments();
                });
            };
            $scope.openAddDocumentModal = function(){
                var mod = $modal.open({
                    templateUrl: 'views/eavrop/order/add-doc-modal.html',
                    size: 'md',
                    controller: addDocModalCtrl,
                });

                mod.result.then(function(result){
                    new Documents(result).$save({eavropId: $stateParams.eavropId});
                    loadDocuments();
                });
            };
        }
    })
    .state('eavrop.order.citizen', {
        url: '/citizen',
        templateUrl: 'views/eavrop/order/citizen.html',
    })
    .state('eavrop.allevents', {
        url: '/all-events',
        resolve: {
            documents: function(EavropDocuments, $stateParams){return EavropDocuments.query({eavropId: $stateParams.eavropId});},
            requestedDocuments: function(EavropRequestedDocuments, $stateParams){return EavropRequestedDocuments.query({eavropId: $stateParams.eavropId});}
        },
        controller: function($scope, documents, requestedDocuments){
            $scope.documents = documents;
            $scope.requestedDocuments = requestedDocuments;
        },
        templateUrl: 'views/eavrop/all-events.html',
    })
    .state('eavrop.compensation', {
        url: '/compensation',
        templateUrl: 'views/eavrop/compensation.html'
    })
    .state('eavrop.investigation', {
        url: '/investigation',
        templateUrl: 'views/eavrop/investigation.html'
    })
    .state('eavrop.notes', {
        url: '/notes',
        templateUrl: 'views/eavrop/notes.html'
    });
});
