(function() {
    'use strict';
    angular.module('fmu.eavrop')
        .controller('ReqAmendmentModalController', ReqAmendmentModalCtrl);
    /*@ngInject*/
    function ReqAmendmentModalCtrl($scope, $modalInstance) {

        $scope.doc = {};
        $scope.save = saveFn;
        $scope.close = closeFn;

        function saveFn() {
            $modalInstance.close($scope.doc);
        }

        function closeFn() {
            $modalInstance.dismiss();
        }
    }
    ReqAmendmentModalCtrl.$inject = ['$scope', '$modalInstance'];
})();