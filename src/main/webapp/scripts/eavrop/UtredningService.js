'use strict';

angular.module('fmuClientApp').factory('UtredningService', ['$http', '$filter', '$q', 'RestUrlBuilderService', 'gettext',
    function($http, $filter, $q, RestUrlBuilderService, gettext) {
        var UTREDNING = {
            dateFormat: 'yyyy-MM-dd',
            statusMapping: {
                INTYG_APPROVED: gettext('Intyg godkänt'),
                INTYG_COMPLEMENT_REQUEST: gettext('Intyg kompleteras'),
                INTYG_SIGNED: gettext('Intyg signeras'),
                EAVROP_APPROVED: gettext('Utredningen godkänts'),
                EAVROP_COMPENSATION_APPROVED: gettext('Utredningens godkänts för utbetalning'),
                UNKNOWN: gettext('Okänt handelse'),
                EXAMINATION: gettext('Besök'),
                BREIFING_WITH_CITIZEN: gettext('Möte med patient'),
                INTERNAL_WORK: gettext('Internt arbete')
            },

            handelseMapping: {
                BOOKED: gettext('Bokat'),
                PERFORMED: gettext('Genomfört'),
                CANCELLED_NOT_PRESENT: gettext('Patient uteblev'),
                CANCELLED_BY_CARE_GIVER: gettext('Besök avbokat av utförare'),
                CANCELLED_LT_48_H: gettext('Besök avbokat <48h'),
                CANCELLED_GT_48_H: gettext('Besök avbokat >48h'),
                CANCELLED_LT_96_H: gettext('Besök avbokat <96h'),
                CANCELLED_GT_96_H: gettext('Besök avbokat >96h')
            },

            tolkMapping: {
                INTERPRETER_BOOKED: gettext('Bokat'),
                INTERPRETATION_PERFORMED: gettext('Tolkning genomförd'),
                INTERPRETER_CANCELED: gettext('Tolk avbokad'),
                INTERPRETER_NOT_PRESENT: gettext('Tolk uteblev'),
                INTERPRETER_PRESENT_BUT_NOT_USED: gettext('Tolk anlänt, men tolkning inte använd')
            },

            eventsRequireConfirmation: {
                CANCELLED_GT_48_H: gettext('Besök avbokat >48h'),
                CANCELLED_NOT_PRESENT: gettext('Patient uteblev'),
                CANCELLED_LT_96_H: gettext('Besök avbokat <96h'),
                CANCELLED_GT_96_H: gettext('Besök avbokat >96h')
            },

            handelseTypes: [{
                type: 'EXAMINATION',
                name: gettext('Besök')
            }, {
                type: 'BREIFING_WITH_CITIZEN',
                name: gettext('Genomgång med patient')
            }, {
                type: 'INTERNAL_WORK',
                name: gettext('Internt arbete')
            }],
            roles: [{
                name: gettext('Läkare')
            }, {
                name: gettext('Psykolog')
            }, {
                name: gettext('Arbetsterapeut')
            }, {
                name: gettext('Sjukgymnast')
            }, {
                name: gettext('Utredare')
            }],


            errors: {
                cannotCreateBooking: 'Bookningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda'
            }
        };

        function getTimeHHMM(hour, minut) {
            var hh = hour < 10 ? '0' + hour : hour;
            var mm = minut < 10 ? '0' + minut : minut;
            return hh + ' : ' + mm;
        }

        function getTextConstants() {
            return UTREDNING;
        }

        return {
            getTextConstants: getTextConstants,
            getAllEvents: function(eavropId) {
                return $http.get(RestUrlBuilderService.buildEventsRestUrl(eavropId)).then(function(data) {
                    // Success
                    return data.data;
                }, function(err) {
                    // Failed to retrieve data
                    return $q.reject(err.data);
                });
            },

            createBooking: function(booking) {
                return $http.post(RestUrlBuilderService.buildCreateBookingRestUrl(), booking)
                    .then(function(data) {
                        // Success
                        return data.data;
                    }, function(err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
            },

            changeBooking: function(dataPackage) {
                return $http.post(RestUrlBuilderService.changeBookingRestUrl(), dataPackage)
                    .then(function(success) {
                        // Success
                        return success.data;
                    }, function(error) {
                        // Error
                        return $q.reject(error.data);
                    });
            },

            changeTolkBooking: function(dataPackage) {
                return $http.post(RestUrlBuilderService.changeTolkBookingRestUrl(), dataPackage)
                    .then(function(success) {
                        // Success
                        return success.data;
                    }, function(error) {
                        // Error
                        return $q.reject(error.data);
                    });
            },

            getTableFields: function() {
                return [{
                        key: 'handelse',
                        name: gettext('Utredning-tabell/Händelse'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'dateOfEvent',
                        name: gettext('Utredning-tabell/Datum'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'timeOfEvent',
                        name: gettext('Utredning-tabell/Tidpunkt'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'utredaPerson',
                        name: gettext('Utredning-tabell/Person'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'role',
                        name: gettext('Utredning-tabell/Roll'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    },

                    {
                        key: 'tolkStatus',
                        name: gettext('Utredning-tabell/Tolk'),
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }, {
                        key: 'handelseStatus',
                        name: gettext('Utredning-tabell/Status'),
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }
                ];
            },

            getTableCellValue: function(key, rowData) {
                var celldata = rowData[key];
                if (celldata === null) {
                    return '-';
                }
                switch (key) {
                    case 'timeOfEvent':
                        var endTime = rowData.timeOfEventEnd;
                        if (endTime !== null) {
                            return getTimeHHMM(celldata.hour, celldata.minute) + ' - ' + getTimeHHMM(endTime.hour, endTime.minute);
                        }

                        return getTimeHHMM(celldata.hour, celldata.minute);
                    case 'dateOfEvent':
                        return $filter('date')(celldata, UTREDNING.dateFormat);
                    case 'tolkStatus':
                        if (celldata.currentStatus === null) {
                            return '-';
                        }
                        return UTREDNING.tolkMapping[celldata.currentStatus.name];
                    case 'handelseStatus':
                        if (celldata.currentStatus === null) {
                            return '-';
                        }
                        return UTREDNING.handelseMapping[celldata.currentStatus.name];
                    case 'handelse':
                        return UTREDNING.statusMapping[celldata];
                    default:
                        return celldata;
                }
            }
        };
    }
]);