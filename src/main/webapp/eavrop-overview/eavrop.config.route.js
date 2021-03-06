(function() {
    'use strict';

    angular.module('fmu.eavrop')
        .run(setUpRoutes);

    /*@ngInject*/
    function setUpRoutes(routeHelper, gettext, Dataservice) {
        var states = [{
            stateName: 'eavrop',
            stateConfig: {
                url: '/eavrop/{eavropId:[A-Za-z0-9-]+}',
                abstract: true,
                resolve: {
                    currentEavrop: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getEavropByID($stateParams.eavropId);
                    }],
                    patientInfo: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getPatientByEavropId($stateParams.eavropId);
                    }],
                    RecievedDocuments: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getRecievedDocuments($stateParams.eavropId);
                    }],
                    ReqDocuments: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getRequestedDocuments($stateParams.eavropId);
                    }],
                    order: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getEavropOrder($stateParams.eavropId);
                    }]
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
                templateUrl: 'eavrop-overview/order/contents.html',
                controller: /* @ngInject */ ['$scope', 'order', function($scope, order) {
                    $scope.order = order;
                }]
            }
        }, {
            stateName: 'eavrop.order.documents',
            stateConfig: {
                title: gettext('Eavrop-document-title/Document'),
                url: '/documents',
                templateUrl: 'eavrop-overview/order/documents/documents.html',
                controller: 'DocumentsController'
            }
        }, {
            stateName: 'eavrop.order.citizen',
            stateConfig: {
                url: '/citizen',
                title: gettext('Eavrop-citizen-title/Citizen'),
                templateUrl: 'eavrop-overview/order/citizen.html',
                resolve: {
                    patient: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getPatientByEavropId($stateParams.eavropId);
                    }]
                },
                controller: /* @ngInject */ ['$scope', 'patient', function($scope, patient) {
                    $scope.patient = patient;
                }]
            }
        }, {
            stateName: 'eavrop.investigation',
            stateConfig: {
                url: '/investigation',
                title: gettext('Eavrop-investigation-title/Investigation'),
                templateUrl: 'eavrop-overview/investigation/investigation.html',
                controller: 'InvestigationController'
            },
        }, {
            stateName: 'eavrop.allevents',
            stateConfig: {
                url: '/all-events',
                title: gettext('Eavrop-AllEvents-title/All events'),
                resolve: {
                    notes: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getNotes($stateParams.eavropId);
                    }],
                    allevents: /* @ngInject */ ['$stateParams', function($stateParams) {
                        return Dataservice.getAllEvents($stateParams.eavropId);
                    }]
                },
                controller: 'AllEventsController',
                templateUrl: 'eavrop-overview/allEvents/all-events.html'
            }
        }, {
            stateName: 'eavrop.compensation',
            stateConfig: {
                url: '/compensation',
                title: gettext('Eavrop-compensation-title/Compensations'),
                templateUrl: 'eavrop-overview/compensations/compensation.html',
                controller: 'CompensationController'
            }
        }, {
            stateName: 'eavrop.notes',
            stateConfig: {
                url: '/notes',
                resolve: {
                    notes: /* @ngInject */ ['$stateParams', function($stateParams){
                        return Dataservice.getNotes($stateParams.eavropId);
                    }]
                },
                templateUrl: 'eavrop-overview/notes/notes.html',
                controller: 'NotesController'
            }
        }];

        routeHelper.registerStates(states);
    }
    setUpRoutes.$inject = ['routeHelper', 'gettext', 'Dataservice'];
})();