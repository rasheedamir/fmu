(function() {
    'use strict';
    angular.module('fmu.eavrop')
        .controller('AddDocModalController', AddDocModalController);
    AddDocModalController.$inject = ['$scope', '$modalInstance'];

    function AddDocModalController($scope, $modalInstance) {
        $scope.doc = {};
        $scope.picker = {
            opened: false
        };
        $scope.open = openFn;
        $scope.save = saveFn;
        $scope.close = closeFn;

        //
        //
        function openFn($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.picker.opened = true;
        }

        function saveFn() {
            $modalInstance.close($scope.doc);
        }

        function closeFn() {
            $modalInstance.dismiss();
        }
    }
})();