(function() {
    'use strict';

    angular.module('fmu.eavrop')
        .run(setUpRoutes);

    setUpRoutes.$inject = ['routeHelper', 'gettext', 'Dataservice', 'logger'];

    function setUpRoutes(routeHelper, gettext, Dataservice) {
        var states = [{
            stateName: 'eavrop',
            stateConfig: {
                url: '/eavrop/{eavropId:[A-Za-z0-9-]+}',
                abstract: true,
                resolve: {
                    currentEavrop: function($stateParams) {
                        return Dataservice.getEavropByID($stateParams.eavropId);
                    },
                    patientInfo: function($stateParams) {
                        return Dataservice.getPatientByEavropId($stateParams.eavropId);
                    }
                },
                controller: 'EavropController',
                templateUrl: 'eavrop-overview/eavrop.html'
            }
        }, {
            stateName: 'eavrop.order',
            stateConfig: {
                url: '/order',
                templateUrl: 'eavrop-overview/order/order.html',
                abstract: true
            }
        }, {
            stateName: 'eavrop.order.contents',
            stateConfig: {
                url: '/contents',
                title: gettext('Eavrop-content-title/Content'),
                resolve: {
                    order: function($stateParams) {
                        return Dataservice.getEavropOrder($stateParams.eavropId);
                    }
                },
                templateUrl: 'eavrop-overview/order/contents.html',
                controller: function($scope, order) {
                    $scope.order = order;
                }
            }
        }, {
            stateName: 'eavrop.order.documents',
            stateConfig: {
                title: gettext('Eavrop-document-title/Document'),
                url: '/documents',
                templateUrl: 'eavrop-overview/order/documents/documents.html',
                resolve: {
                    RecievedDocuments: function($stateParams) {
                        return Dataservice.getRecievedDocuments($stateParams.eavropId);
                    },
                    ReqDocuments: function($stateParams) {
                        return Dataservice.getRequestedDocuments($stateParams.eavropId);
                    }
                },
                controller: 'DocumentsController'
            }
        }, {
            stateName: 'eavrop.order.citizen',
            stateConfig: {
                url: '/citizen',
                title: gettext('Eavrop-citizen-title/Citizen'),
                templateUrl: 'eavrop-overview/order/citizen.html',
                resolve: {
                    patient: function($stateParams) {
                        return Dataservice.getPatientByEavropId($stateParams.eavropId);
                    }
                },
                controller: function($scope, patient) {
                    $scope.patient = patient;
                }
            }
        }, {
            stateName: 'eavrop.investigation',
            stateConfig: {
                url: '/investigation',
                title: gettext('Eavrop-investigation-title/Investigation'),
                templateUrl: 'eavrop-overview/investigation/investigation.html',
                controller: 'InvestigationController'
            }
        }];

        routeHelper.registerStates(states);
    }
})();