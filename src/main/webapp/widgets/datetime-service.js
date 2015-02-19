(function() {
    'use strict';

    angular.module('fmu.widgets')
        .factory('DatetimeService', DatetimeService);

    function DatetimeService() {
        var startDate = new Date();
        var endDate = new Date();
        endDate.setMonth(startDate.getMonth() + 1);
        startDate.setMonth(startDate.getMonth() - 1);

        return {
            startDate: startDate,
            endDate: endDate
        };
    }
})();