(function() {
    'use strict';

    angular.module('fmu.overview')
        .controller('CompletedController', CompletedController);
    CompletedController.$inject = ['$scope', '$filter', 'AuthService', 'DatetimeService', 'eavropService', 'gettext'];

    function CompletedController($scope, $filter, AuthService, DatetimeService, eavropService, gettext) {
        $scope.authService = AuthService;
        $scope.dateKey = 'creationTime';
        $scope.datetimeService = DatetimeService;

        $scope.completedStatus = eavropService.getEavropConstants.eavropStatus.completed;
        $scope.headerFields = [{
                key: 'arendeId',
                name: gettext('Genomförda-utredningar/Ärende-ID')
            }, {
                key: 'utredningType',
                name: gettext('Genomförda-utredningar/Typ')
            }, {
                key: 'dagarFromStartToAccepted',
                name: gettext('Genomförda-utredningar/Antal dgr klar')
            }, {
                key: 'totalCompletionDays',
                name: gettext('Genomförda-utredningar/Antal dgr för komplettering')
            }, {
                key: 'avikelser',
                name: gettext('Genomförda-utredningar/avikelser')
            },

            {
                key: 'utredareOrganisation',
                name: gettext('Genomförda-utredningar/Utredare, organisation')
            }, {
                key: 'ansvarigUtredare',
                name: gettext('Genomförda-utredningar/Utredare, ansvarig')
            }, {
                key: 'dateIntygDelivered',
                name: gettext('Genomförda-utredningar/Intyg levererades, datum')
            }, {
                key: 'isCompleted',
                name: gettext('Genomförda-utredningar/Utredning komplett') + '&nbsp;?'
            }, {
                key: 'isCompensationApproved',
                name: gettext('Genomförda-utredningar/Godkänd för ersättning')
            }
        ];
        $scope.headerGroups = [{
            name: null,
            colorClass: null,
            colspan: 10
        }];

        $scope.footerHints = [{
            description: gettext('Genomförda-utredningar/Invänta acceptans av intyg och godkännande för ersättning'),
            colorClass: null
        }, {
            description: gettext('Genomförda-utredningar/Utredningen är ej komplett, ej godkänd, försenad eller innehåller ersättningsbara avvikelser'),
            colorClass: 'bg-danger'
        }, {
            description: gettext('Genomförda-utredningar/Utredning komplett, inväntar godkännande för ersättning'),
            colorClass: 'bg-warning'
        }, {
            description: gettext('Genomförda-utredningar/Utredning är komplett och godkänd'),
            colorClass: 'bg-success'
        }];

        $scope.datePickerDescription = gettext('Genomförda-utredningar/Datumen utgår från det datum då intyg levererats');

        $scope.visa = visaFn;
        $scope.getTableCellValue = getTableCellValueFn;

        function visaFn() {
            if ($scope.tableParameters) {
                $scope.tableParameters.reload();
            }
        }

        function getTableCellValueFn(key, rowData) {
            var value = rowData[key];
            var eavropConstants = eavropService.getEavropConstants;
            switch (key) {
                case 'dateIntygDelivered':
                    return $filter('date')(value, eavropConstants.dateFormat);
                case 'isCompleted':
                    return eavropConstants.isCompletedMapping[value];
                case 'isCompensationApproved':
                    var status = $filter('translate')(eavropConstants.isCompensationApprovedMapping[value]);
                    if (value === null) {
                        return status;
                    } else {
                        return status + ', ' + $filter('date')(rowData.compensationApprovalDate, eavropConstants.dateFormat);
                    }
                    break;
                default:
                    return value === null ? '-' : value;
            }
        }
    }
})();