(function() {
    'use strict';
    angular.module('fmu.eavrop').
    controller('AddBookingModalController', AddBookingController);

    /*@ngInject*/
    function AddBookingController($scope, Eavrop, tableParameters, investigationService, $modalInstance) {
        $scope.handelseDate = new Date();
        $scope.handelseStartTime = new Date();
        $scope.handelseStartTime.setMinutes(0);
        $scope.handelseEndTime = new Date();
        $scope.handelseEndTime.setMinutes(0);
        $scope.createBookingErrors = [];
        $scope.tillaggRadio = {
            value: false
        };
        $scope.tolkRadio = false;
        $scope.handelseTypes = investigationService.getTextConstants().handelseTypes;
        $scope.roles = investigationService.getTextConstants().roles;
        $scope.isAFU = isAFUFn;
        $scope.saveHandelse = saveHandelseFn;
        $scope.cancel = cancelFn;




        function isAFUFn() {
            return Eavrop && Eavrop.utredningType === 'AFU';
        }

        function constructBookingObject() {
            return {
                eavropId: Eavrop.eavropId,
                bookingType: $scope.choosenHandelseType ? $scope.choosenHandelseType.type : null,
                bookingDate: $scope.handelseDate.getTime(),
                bookingStartTime: {
                    hour: $scope.handelseStartTime.getHours(),
                    minute: $scope.handelseStartTime.getMinutes()
                },
                additionalService: $scope.tillaggRadio.value,
                bookingEndTime: {
                    hour: $scope.handelseEndTime.getHours(),
                    minute: $scope.handelseEndTime.getMinutes()
                },
                personName: $scope.personName,
                personRole: $scope.choosenRole ? $scope.choosenRole.name : null,
                useInterpreter: $scope.tolkRadio
            };
        }

        function saveHandelseFn() {
            var dataPackage = constructBookingObject();
            var promise = investigationService.createBooking(dataPackage);
            promise.then(function() {
                if($modalInstance) {
                    $modalInstance.close();
                }
                tableParameters.reload();
            }, function() {
                $scope.createBookingErrors.push(investigationService.getTextConstants().errors.cannotCreateBooking);
            });
        }

        function cancelFn() {
            $modalInstance.dismiss('cancel');
        }
    }
    AddBookingController.$inject = ['$scope', 'Eavrop', 'tableParameters', 'investigationService', '$modalInstance'];
})();