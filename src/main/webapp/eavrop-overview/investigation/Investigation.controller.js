(function() {
    'use strict';

    angular.module('fmu.eavrop')
        .controller('InvestigationController', InvestigationController);
    /*@ngInject*/
    function InvestigationController($scope, $filter, $stateParams, AuthService, investigationService, $modal, logger, currentEavrop) {
        $scope.authService = AuthService;
        $scope.currentEavropId = $stateParams.eavropId;
        $scope.headerFields = investigationService.getTableFields();
        $scope.getTableCellValue = investigationService.getTableCellValue;
        $scope.openBookingCreationDialog = openBookingCreationDialogFn;

        function openBookingCreationDialogFn() {
            $modal.open({
                templateUrl: 'eavrop-overview/investigation/addBookingModal.html',
                resolve: {
                    Eavrop: function() {
                        return currentEavrop;
                    },

                    tableParameters: function() {
                        return $scope.tableParameters;
                    }
                },
                controller: 'AddBookingModalController'
            });
        }
    }
    InvestigationController.$inject = ['$scope', '$filter', '$stateParams', 'AuthService', 'investigationService', '$modal', 'logger', 'currentEavrop'];
})();