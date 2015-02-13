(function() {
    'use strict';

    angular
        .module('rest')
        .factory('Dataservice', dataservice);

    dataservice.$inject = ['$resource', 'RESTURL', 'logger', 'EAVROP_STATES'];
    function dataservice($resource, RESTURL, logger, EAVROP_STATES) {
        var service = {
            getEavrops: getEavrops,
            getIncomingEavrops: getIncomingEavrops,
            getOngoingEavrops: getOngoingEavrops,
            getCompletedEavrops: getCompletedEavrops
        };

        return service;

        function getIncomingEavrops(fromdate, todate, page, pagesize, sortkey, sortorder) {
            return getEavrops(fromdate, todate, EAVROP_STATES.incoming, page, pagesize, sortkey, sortorder);
        }

        function getOngoingEavrops(fromdate, todate, page, pagesize, sortkey, sortorder) {
            return getEavrops(fromdate, todate, EAVROP_STATES.ongoing, page, pagesize, sortkey, sortorder);
        }

        function getCompletedEavrops(fromdate, todate, page, pagesize, sortkey, sortorder) {
            return getEavrops(fromdate, todate, EAVROP_STATES.completed, page, pagesize, sortkey, sortorder);
        }

        function getEavrops(fromdate, todate, status, page, pagesize, sortkey, sortorder) {
            var eavropResource =  $resource(RESTURL.overview.incoming, {
                fromdate : '@fromdate', 
                todate : '@todate', 
                status : '@status', 
                page : '@page', 
                pagesize : '@pagesize', 
                sortkey : '@sortkey', 
                sortorder : '@sortorder'
            });

            return eavropResource.get({
                fromdate : fromdate, 
                todate : todate, 
                status : status, 
                page : page ? page : 0, 
                pagesize : pagesize ? pagesize : 10, 
                sortkey : sortkey ? sortkey : 'arendeId', 
                sortorder : sortorder ? sortorder : 'ASC'
            });
        }
    }
})();