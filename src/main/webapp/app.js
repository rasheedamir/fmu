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
    'ui.bootstrap',
    'ngAnimate',
    'gettext'
])
.run(['gettextCatalog', function(gettextCatalog){
    gettextCatalog.currentLanguage='sv';
    //gettextCatalog.debug = true;
}])
.run(['$rootScope', '$state', function($rootScope, $state){
    $rootScope.$state = $state;
}])
.run(['$rootScope', 'AuthService', function($rootScope, AuthService){
	$rootScope.userInfo = AuthService.getUserInfo();
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
        templateUrl: 'orders-overview/overview.html'
    })
    .state('overview.orders', {
        url: '/orders',
        templateUrl: 'orders-overview/incoming/incoming.html',
        controller: 'OrderController'
    })
    .state('overview.ongoing', {
        url: '/ongoing',
        templateUrl: 'orders-overview/ongoing/ongoing.html',
        controller: 'OngoingController'
    })
    .state('overview.completed', {
        url: '/completed',
        templateUrl: 'orders-overview/completed/completed.html',
        controller: 'CompletedController'
    })
    .state('eavrop', {
        url: '/eavrop/{eavropId:[A-Za-z0-9-]+}',
        abstract: true,
        resolve: {
            currentEavrop: function($stateParams, Eavrops){
                return Eavrops.get({eavropId: $stateParams.eavropId});
            },
            patientInfo: function($stateParams, EavropPatient){
            	return EavropPatient.get({eavropId: $stateParams.eavropId});
            }
        },
        controller: 'EavropCtrl',
        templateUrl: 'eavrop-overview/eavrop.html'
    })
    .state('eavrop.order', {
        url: '/order',
        templateUrl: 'eavrop-overview/order/order.html',
        abstract: true
    })
    .state('eavrop.order.contents', {
        url: '/contents',
        resolve: {
        	order: function($stateParams, EavropOrder){
        		return EavropOrder.get({eavropId: $stateParams.eavropId});
        	}
        },
        templateUrl: 'eavrop-overview/order/contents.html',
        controller: function($scope, order){
        	$scope.order = order;
        }
    })
    .state('eavrop.order.documents', {
        url: '/documents',
        templateUrl: 'eavrop-overview/order/documents.html',
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
                    templateUrl: 'eavrop-overview/order/req-amendment-modal.html',
                    size: 'md',
                    controller: reqAmendmentModalCtrl
                });

                mod.result.then(function(result){
                    new ReqDocuments(result).$save({eavropId: $stateParams.eavropId}).then(function(){
                        loadReqDocuments();
                    });
                });
            };
            $scope.openAddDocumentModal = function(){
                var mod = $modal.open({
                    templateUrl: 'eavrop-overview/order/add-doc-modal.html',
                    size: 'md',
                    controller: addDocModalCtrl
                });

                mod.result.then(function(result){
                	var payload = {
                			name: result.name,
                			regDate: result.regDate.getTime()
                	};
                    new Documents(payload).$save({eavropId: $stateParams.eavropId}).then(function(){
                        loadDocuments();
                    });
                });
            };
        }
    })
    .state('eavrop.order.citizen', {
        url: '/citizen',
        templateUrl: 'eavrop-overview/order/citizen.html',
        resolve:{
        	patient: function(EavropPatient, $stateParams){return EavropPatient.get({eavropId: $stateParams.eavropId});}
        },
        controller: function($scope, patient){
        	$scope.patient = patient;
        }
    })
    .state('eavrop.allevents', {
        url: '/all-events',
        resolve: {
            documents: function(EavropDocuments, $stateParams){return EavropDocuments.query({eavropId: $stateParams.eavropId});},
            requestedDocuments: function(EavropRequestedDocuments, $stateParams){return EavropRequestedDocuments.query({eavropId: $stateParams.eavropId});},
            notes: function(EavropNotes, $stateParams){return EavropNotes.query({eavropId: $stateParams.eavropId});},
            allevents: function(EavropAllEvents, $stateParams){return EavropAllEvents.get({eavropId: $stateParams.eavropId});},
            order: function(EavropOrder, $stateParams){return EavropOrder.get({eavropId: $stateParams.eavropId});}
        },
        controller: function($scope,$stateParams, documents, requestedDocuments, notes, allevents, order, UtredningService){
            $scope.headerFields = UtredningService.getTableFields();
            $scope.getTableCellValue = UtredningService.getTableCellValue;
            $scope.currentEavropId = $stateParams.eavropId;

            $scope.documents = documents;
            $scope.notes = notes;
            $scope.requestedDocuments = requestedDocuments;
            $scope.allevents = allevents;
            $scope.order = order;
        },
        templateUrl: 'eavrop-overview/allEvents/all-events.html'
    })
    .state('eavrop.compensation', {
        url: '/compensation',
        templateUrl: 'eavrop-overview/compensations/compensation.html',
        controller: 'CompensationController'
    })
    .state('eavrop.investigation', {
        url: '/investigation',
        templateUrl: 'eavrop-overview/investigation/investigation.html',
        controller: 'InvestigationController'
    })
    .state('eavrop.notes', {
        url: '/notes',
        templateUrl: 'eavrop-overview/notes/notes.html',
        controller: function($scope, $modal, $filter, $stateParams, EavropNotes, EavropService, gettext){
            var EAVROP_NOTES = {
                cannotAdd: gettext('Anteckningar/Anteckningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda'),
                cannotRemove: gettext('Anteckningar/Fel uppstod vid bortagning av anteckning, detta kan beror på att du inte har rätt behörighet att genomföra denna förfrågan')
            };
            $scope.openRemoveNote = function (noteData) {
                var confirmModal = $modal.open({
                    templateUrl: 'eavrop-overview/notes/confirmRemoveModal.html',
                    size: 'md',
                    resolve: {
                        noteData: function () {
                            return noteData;
                        }
                    },
                    controller: function ($scope, noteData) {
                        $scope.removeNote = function () {
                            if(noteData && noteData.removable){
                                var promise = EavropService.removeNote($stateParams.eavropId, noteData.noteId);
                                promise.then(function () {
                                    confirmModal.close();
                                    loadNotes();
                                }, function () {
                                    $scope.noteError = [EAVROP_NOTES.cannotRemove];
                                });
                            }
                        };

                        $scope.cancelRemoval = function () {
                            confirmModal.close();
                        };
                    }
                });
            };

            function loadNotes(){
                $scope.notes = EavropNotes.query({eavropId: $stateParams.eavropId});
            }
            loadNotes();
            $scope.openAddNoteModal = function(){
                var modalInstance = $modal.open({
                    templateUrl: 'eavrop-overview/notes/add-note-modal.html',
                    size: 'md',
                    controller: function($scope, EavropNotes){
                        $scope.picker = {opened: false};
                        $scope.note = new EavropNotes({
                            content: '',
                            createdDate: new Date()
                        });
                        $scope.open = function($event){
                            $event.preventDefault();
                            $event.stopPropagation();
                            $scope.picker.opened = true;
                        };

                        function createNoteDateObject(){
                            return {
                                eavropId: $stateParams.eavropId,
                                text: $scope.note.content
                            };
                        }

                        $scope.save = function(){
                            var promise = EavropService.addNote(createNoteDateObject());
                            promise.then(function () {
                                // Success
                                modalInstance.close();
                                loadNotes();
                            }, function () {
                                // Failed
                                $scope.noteError = [EAVROP_NOTES.cannotAdd];
                            });
                        };

                        $scope.close = function(){
                            modalInstance.dismiss();
                        };
                    }
                });
            };
        }
    });
});
