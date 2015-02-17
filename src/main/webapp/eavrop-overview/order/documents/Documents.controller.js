(function() {
    'use strict';

    angular.module('fmu.eavrop').
    controller('DocumentsController', DocumentsController);

    DocumentsController.$inject = ['$scope', 'Dataservice', 'RecievedDocuments', 'ReqDocuments', '$modal', '$stateParams', 'logger'];

    function DocumentsController($scope, Dataservice, RecievedDocuments, ReqDocuments, $modal, $stateParams, logger) {
        $scope.openReqAmendmentModal = openReqAmendmentModalfn;
        $scope.openAddDocumentModal = openAddDocumentModalfn;
        $scope.documents = RecievedDocuments;
        $scope.reqDocuments = ReqDocuments;


        function loadDocuments() {
            $scope.documents = Dataservice.getRecievedDocuments($stateParams.eavropId);
        }

        function loadReqDocuments() {
            $scope.reqDocuments = Dataservice.getRequestedDocuments($stateParams.eavropId);
        }

        function openReqAmendmentModalfn() {
            var mod = $modal.open({
                templateUrl: 'eavrop-overview/order/documents/req-amendment-modal.html',
                size: 'md',
                controller: 'ReqAmendmentModalController'
            });

            mod.result.then(function(result) {
                Dataservice.saverequestedDocuments($stateParams.eavropId, result)
                    .then(function() {
                        loadReqDocuments();
                    });
            });
        }

        function openAddDocumentModalfn() {
            var mod = $modal.open({
                templateUrl: 'eavrop-overview/order/documents/add-doc-modal.html',
                size: 'md',
                controller: 'AddDocModalController'
            });

            logger.info('mod', mod);

            mod.result.then(function(result) {
                var payload = {
                    name: result.name,
                    regDate: result.regDate.getTime()
                };
                Dataservice.saveRecievedDocuments($stateParams.eavropId, payload).then(function() {
                    loadDocuments();
                });
            });
        }
    }
})();