'use strict';

angular.module('fmu.eavrop')
    .controller('RequestAcceptanceController', RequestAcceptanceController);

RequestAcceptanceController.$inject = ['$scope', '$modal', '$stateParams', '$state', 'Dataservice'];
function RequestAcceptanceController($scope, $modal, $stateParams, $state, Dataservice) {
    $scope.acceptDialog = function() {
        $modal.open({
            templateUrl: 'eavrop-overview/accept-request-modal.html',
            size: 'md',
            resolve: {},
            controller: function($scope, $modalInstance, $stateParams, $state) {

                $scope.close = function() {
                    $modalInstance.dismiss('cancel');
                };

                $scope.accept = function() {
                    var result = Dataservice.acceptEavrop($stateParams.eavropId);
                    result.then(function() {
                        $state.transitionTo($state.current, $stateParams, {
                            reload: true,
                            inherit: false,
                            notify: true
                        });

                        $modalInstance.close();
                    });
                };
            }
        });
    };

    $scope.rejectDialog = function() {
        $modal.open({
            templateUrl: 'eavrop-overview/reject-request-modal.html',
            size: 'md',
            resolve: {},
            controller: function($scope, $modalInstance, $stateParams, $state) {

                $scope.close = function() {
                    $modalInstance.dismiss('cancel');
                };

                $scope.reject = function() {
                    var result = Dataservice.rejectEavrop($stateParams.eavropId);
                    result.then(function() {
                        $state.transitionTo($state.current, $stateParams, {
                            reload: true,
                            inherit: false,
                            notify: true
                        });

                        $modalInstance.close();
                        window.location.href = window.location.origin;
                    });
                };
            }
        });
    };
}