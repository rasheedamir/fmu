(function() {
    'use strict';

    angular.module('fmu.eavrop')
        .controller('CompensationController', CompensationController);
    /*@ngInject*/
    function CompensationController($scope, $filter, AuthService, currentEavrop, Dataservice, gettext) {
        $scope.authService = AuthService;
        $scope.currentEavrop = currentEavrop;
        $scope.arendeHeaderFields = [{
                key: 'arendeId',
                name: gettext('Underlag-ersättningsvy/Ärende-ID')
            }, {
                key: 'utredningType',
                name: gettext('Underlag-ersättningsvy/Typ')
            }, {
                key: 'utforareOrganisation',
                name: gettext('Underlag-ersättningsvy/Utförare, organisation')
            }, {
                key: 'utforareNamn',
                name: gettext('Underlag-ersättningsvy/Utförare, namn')
            }, {
                key: 'tolkBooked',
                name: gettext('Underlag-ersättningsvy/Tolk anlitad?')
            }, {
                key: 'utredningDuration',
                name: gettext('Underlag-ersättningsvy/Utredningen genomfördes på, antal dagar')
            }, {
                key: 'nrDaysAfterCompletetion',
                name: gettext('Underlag-ersättningsvy/Antal dagar efter komplettering')
            }, {
                key: 'nrAvikelser',
                name: gettext('Underlag-ersättningsvy/Antal avikelser')
            }, {
                key: 'nrUtredningstarts',
                name: gettext('Underlag-ersättningsvy/Antal utredningsstarter')
            }, {
                key: 'isCompletedAndApproved',
                name: gettext('Underlag-ersättningsvy/Utredning är komplett och godkänd?')
            }

        ];

        var textMapping = {
            statusMapping: {
                CANCELLED_NOT_PRESENT: gettext('Underlag-ersättningsvy/Patient uteblev'),
                CANCELLED_BY_CAREGIVER: gettext('Underlag-ersättningsvy/Besök avbokat av utförare'),
                CANCELLED_LT_48_H: gettext('Underlag-ersättningsvy/Besök avbokat <48h'),
                CANCELLED_GT_48_H: gettext('Underlag-ersättningsvy/Besök avbokat >48h'),
                CANCELLED_LT_96_H: gettext('Underlag-ersättningsvy/Besök avbokat <96h'),
                CANCELLED_GT_96_H: gettext('Underlag-ersättningsvy/Besök avbokat >96h'),
                INTERPRETER_NOT_PRESENT: gettext('Underlag-ersättningsvy/Tolk uteblev'),
                INTYG_COMPLEMENT_RESPONSE_DEVIATION: gettext('Underlag-ersättningsvy/Antal dagar för komplettering har överskridits'),
                EAVROP_ASSIGNMENT_ACCEPT_DEVIATION: gettext('Underlag-ersättningsvy/Antal dagar för acceptans av förfrågan om utredning har överskridits'),
                EAVROP_ASSESSMENT_LENGHT_DEVIATION: gettext('Underlag-ersättningsvy/Antal dagar för utredning har överskridits'),
                UNKNOWN: gettext('Underlag-ersättningsvy/Ops detta ska inte hända')
            },
            jaNejMapping: {
                true: gettext('Underlag-ersättningsvy/Ja'),
                false: gettext('Underlag-ersättningsvy/Nej')
            }
        };
        $scope.getArendeData = getArendeDataFn;
        $scope.getAvikelserData = getAvikelserDataFn;
        $scope.getTillaggData = getTillaggDataFn;
        initCompensationTable().then(fetchTableData);

        function getArendeDataFn(key, data) {
            var value = data ? data[key] : '-';
            switch (key) {
                case 'tolkBooked':
                case 'isCompletedAndApproved':
                    return textMapping.jaNejMapping[value];
                default:
                    return value ? value : '-';
            }
        }

        function getAvikelserDataFn(key, data) {
            return data ? textMapping.statusMapping[data[key]] : '-';
        }

        function getTillaggDataFn(key, data) {
            var value = data ? data[key] : '-';
            switch (key) {
                case 'antalTimmar':
                    return millisToHHMM(value);
                case 'tolkBooked':
                    return textMapping.jaNejMapping[value];
                default:
                    return value ? value : '-';
            }
        }

        function millisToHHMM(milliseconds) {
            var minutes = Math.floor(milliseconds / (1000 * 60));
            var hours = Math.floor(milliseconds / (1000 * 60 * 60));
            var bookingMinutes = (minutes - hours * 60);
            return (hours < 10 ? '0' + hours : hours) + ' : ' + (bookingMinutes < 10 ? '0' + bookingMinutes : bookingMinutes);
        }

        function initCompensationTable() {
            return currentEavrop.$promise.then(
                function() {
                    // Init table fields
                    if (!$scope.specificationHeaderFields) {
                        $scope.specificationHeaderFields = [{
                                key: 'deviationType',
                                name: gettext('Underlag-ersättningsvy/Avvikelse')
                            }

                        ];
                    }

                    if (!$scope.tillaggtjanstHeaderFields && currentEavrop.utredningType === 'AFU') {
                        $scope.tillaggtjanstHeaderFields = [{
                            key: 'name',
                            name: gettext('Underlag-ersättningsvy/Tillägstjänst')
                        }, {
                            key: 'antalTimmar',
                            name: gettext('Underlag-ersättningsvy/Timmar')
                        }, {
                            key: 'tolkBooked',
                            name: gettext('Underlag-ersättningsvy/Tolk')
                        }];
                    }

                    return currentEavrop.eavropId;
                }
            );
        }

        function fetchTableData(eavropId) {
            return Dataservice.getCompensation(eavropId).$promise.then(function(serverData) {
                $scope.tableData = [serverData];
                $scope.specifications = serverData.avikelser;
                $scope.tillaggTjanster = serverData.tillaggTjanster;
                return serverData;
            });
        }
    }
    CompensationController.$inject = ['$scope', '$filter', 'AuthService', 'currentEavrop', 'Dataservice', 'gettext'];
})();