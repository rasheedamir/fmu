(function() {
    'use strict';

    angular.module('fmu.overview')
        .controller('OrderController', OrderController);

    /*@ngInject*/
    function OrderController($scope, $state, $filter, AuthService, DatetimeService, eavropService, gettext) {
        $scope.authService = AuthService;
        $scope.dateKey = 'creationTime';
        $scope.dateTimeService = DatetimeService;
        $scope.click = clickFn;
        $scope.visa = visaFn;
        $scope.bestallningarStatus = eavropService.getEavropConstants.eavropStatus.notAccepted;
        $scope.initTableParameters = initTableParametersFn;
        $scope.footerHints = [{
            description: gettext('Beställningar/Antal dagar har överträtts och/eller annan avvikelse finns'),
            colorClass: 'bg-danger'
        }];
        $scope.datePickerDescription = gettext('Beställningar/Datumen utgår från det datum då beställningen inkommit');
        $scope.getTableCellValue = getTableCellValueFn;

        // TODO fix this with event ?
        $scope.$watch('authService', function() {
            $scope.initTableParameters();
        }, true);


        function clickFn(id) {
            $state.go('eavrop.order', {
                eavropId: id
            });
        }

        function visaFn() {
            if ($scope.tableParameters) {
                $scope.tableParameters.reload();
            }
        }

        function initTableParametersFn() {
            if (AuthService.hasRole('ROLE_SAMORDNARE')) {
                $scope.headerFields = [{
                        key: 'arendeId',
                        name: gettext('Beställningar/Ärende-ID')
                    }, {
                        key: 'utredningType',
                        name: gettext('Beställningar/Typ')
                    }, {
                        key: 'bestallareOrganisation',
                        name: gettext('Beställningar/Organisation')
                    }, {
                        key: 'bestallareEnhet',
                        name: gettext('Beställningar/Enhet/Avdelning')
                    }, {
                        key: 'creationTime',
                        name: gettext('Beställningar/Förfrågan inkommit datum')
                    }, {
                        key: 'patientCity',
                        name: gettext('Beställningar/Den försäkrades bostadsort')
                    }, {
                        key: 'mottagarenOrganisation',
                        name: gettext('Beställningar/Organisation')
                    }, {
                        key: 'utredareOrganisation',
                        name: gettext('Beställningar/Enhet')
                    }, {
                        key: 'status',
                        name: gettext('Beställningar/Status')
                    }, {
                        key: 'antalDagarEfterForfragan',
                        name: gettext('Beställningar/Antal dagar efter förfrågan om utredning')
                    }

                ];

                $scope.headerGroups = [{
                    name: null,
                    colorClass: null,
                    colspan: 2
                }, {
                    name: gettext('Beställningar/beställare'),
                    colorClass: 'bg-head-danger',
                    colspan: 4
                }, {
                    name: gettext('Beställningar/leverantör'),
                    colorClass: 'bg-head-warning',
                    colspan: 2
                }, {
                    name: null,
                    colorClass: null,
                    colspan: 2
                }];
            } else {
                $scope.headerFields = [{
                        key: 'arendeId',
                        name: gettext('Beställningar/Ärende-ID')
                    }, {
                        key: 'utredningType',
                        name: gettext('Beställningar/Typ')
                    }, {
                        key: 'bestallareOrganisation',
                        name: gettext('Beställningar/Organisation')
                    }, {
                        key: 'bestallareEnhet',
                        name: gettext('Beställningar/Enhet/Avdelning')
                    }, {
                        key: 'creationTime',
                        name: gettext('Beställningar/Förfrågan inkommit datum')
                    }, {
                        key: 'mottagarenOrganisation',
                        name: gettext('Beställningar/Organisation')
                    }, {
                        key: 'utredareOrganisation',
                        name: gettext('Beställningar/Enhet')
                    }, {
                        key: 'status',
                        name: gettext('Beställningar/Status')
                    }, {
                        key: 'antalDagarEfterForfragan',
                        name: gettext('Beställningar/Antal dagar efter förfrågan om utredning')
                    }

                ];

                $scope.headerGroups = [{
                    name: null,
                    colorClass: null,
                    colspan: 2
                }, {
                    name: gettext('Beställningar/beställare'),
                    colorClass: 'bg-head-danger',
                    colspan: 3
                }, {
                    name: gettext('Beställningar/leverantör'),
                    colorClass: 'bg-head-warning',
                    colspan: 2
                }, {
                    name: null,
                    colorClass: null,
                    colspan: 2
                }];
            }
        }

        function getTableCellValueFn(key, rowData) {
            switch (key) {
                case 'creationTime':
                    return $filter('date')(rowData[key], eavropService.getEavropConstants.dateFormat);
                case 'status':
                    return eavropService.getEavropConstants.statusMapping[rowData[key]];
                default:
                    return rowData[key] ? rowData[key] : '';
            }
        }
    }
    OrderController.$inject = ['$scope', '$state', '$filter', 'AuthService', 'DatetimeService', 'eavropService', 'gettext'];
})();