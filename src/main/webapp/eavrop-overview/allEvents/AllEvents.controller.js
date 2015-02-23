(function() {
    'use strict';
    angular.module('fmu.eavrop')
        .controller('AllEventsController', AllEventsController);

    /*@ngInject*/
    function AllEventsController($scope, $stateParams, RecievedDocuments, ReqDocuments, notes, allevents, order, investigationService) {
        $scope.headerFields = investigationService.getTableFields();
        $scope.getTableCellValue = investigationService.getTableCellValue;
        $scope.currentEavropId = $stateParams.eavropId;
        $scope.documents = RecievedDocuments;
        $scope.notes = notes;
        $scope.requestedDocuments = ReqDocuments;
        $scope.allevents = allevents;
        $scope.order = order;
    }
    AllEventsController.$inject = ['$scope', '$stateParams', 'RecievedDocuments', 'ReqDocuments', 'notes', 'allevents', 'order', 'investigationService'];
})();