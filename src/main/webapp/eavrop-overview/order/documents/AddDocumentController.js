(function() {
    'use strict';
    angular.module('fmu')
        .controller('AddDocumentController', AddDocumentController);
    AddDocumentController.$inject = ['$scope', '$modalInstance', 'currentEavrop'];

    function AddDocumentController($scope, $modalInstance, currentEavrop) {

        $scope.currentEavrop = currentEavrop;
        $scope.picker = {
            opened: false
        };
        $scope.doc = {
            regDate: new Date()
        };
        $scope.open = openFn;
        $scope.save = saveFn;
        $scope.close = closeFn;


        function openFn($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.picker.opened = true;
        }

        function saveFn() {
            $scope.currentEavrop.documents.push($scope.doc);
            $scope.currentEavrop.$update();
            $modalInstance.close();
        }

        function closeFn() {
            $modalInstance.dismiss('cancel');
        }
    }
})();