(function() {
    'use strict';

    angular
        .module('fmu.core')
        .factory('Dataservice', dataservice);

    dataservice.$inject = ['$resource', 'RESTURL', 'logger', 'EAVROP_STATES'];

    function dataservice($resource, RESTURL, logger, EAVROP_STATES) {
        var service = {
            getOverviewEavrops: getOverviewEavrops,
            getIncomingEavrops: getIncomingEavrops,
            getOngoingEavrops: getOngoingEavrops,
            getCompletedEavrops: getCompletedEavrops,
            getEavropByID: getEavropByID,
            getPatientByEavropId: getPatientByEavropId,
            getVardgivarenhetByEavropId: getVardgivarenhetByEavropId,
            assignEavropToVardgivarEnhet: assignEavropToVardgivarEnhet,
            acceptEavrop: acceptEavrop,
            rejectEavrop: rejectEavrop,
            getEavropOrder: getEavropOrder
        };

        return service;

        function getIncomingEavrops(fromdate, todate, page, pagesize, sortkey, sortorder) {
            return getOverviewEavrops(fromdate, todate, EAVROP_STATES.incoming, page, pagesize, sortkey, sortorder);
        }

        function getOngoingEavrops(fromdate, todate, page, pagesize, sortkey, sortorder) {
            return getOverviewEavrops(fromdate, todate, EAVROP_STATES.ongoing, page, pagesize, sortkey, sortorder);
        }

        function getCompletedEavrops(fromdate, todate, page, pagesize, sortkey, sortorder) {
            return getOverviewEavrops(fromdate, todate, EAVROP_STATES.completed, page, pagesize, sortkey, sortorder);
        }

        function getOverviewEavrops(fromdate, todate, status, page, pagesize, sortkey, sortorder) {
            var eavropResource = $resource(RESTURL.overview.incoming, {
                fromdate: '@fromdate',
                todate: '@todate',
                status: '@status',
                page: '@page',
                pagesize: '@pagesize',
                sortkey: '@sortkey',
                sortorder: '@sortorder'
            });

            return eavropResource.get({
                fromdate: fromdate,
                todate: todate,
                status: status,
                page: page ? page : 0,
                pagesize: pagesize ? pagesize : 10,
                sortkey: sortkey ? sortkey : 'arendeId',
                sortorder: sortorder ? sortorder : 'ASC'
            });
        }

        function getEavropByID(eavropId) {
            var resource = $resource(RESTURL.eavrop, {
                eavropId: '@eavropId'
            });

            resource.get({
                eavropId: eavropId
            });
        }

        function getPatientByEavropId(eavropId) {
            var resource = $resource(RESTURL.eavropPatient, {
                eavropId: '@eavropId'
            });

            return resource.get({
                eavropId: eavropId
            });
        }

        function getVardgivarenhetByEavropId(eavropId) {
            var resource = $resource(RESTURL.eavropVardgivarenheter, {
                eavropId: '@eavropId'
            });

            return resource.query({
                eavropId: eavropId
            });
        }

        function assignEavropToVardgivarEnhet(eavropId, vardgivarenhet) {
            var EavropAssignment = $resource(RESTURL.eavropAssignment, {
                eavropId: '@eavropId'
            }, {
                'assign': {
                    method: 'PUT'
                }
            });

            var resource = new EavropAssignment({
                eavropId: eavropId
            });

            return resource.$assign({
                veId: vardgivarenhet
            });
        }

        function acceptEavrop(eavropId) {
            var EavropAccept = $resource(RESTURL.eavropAccept, {
                eavropId: '@eavropId'
            }, {
                'accept': {
                    method: 'PUT'
                }
            });

            var res = new EavropAccept({
                eavropId: eavropId
            });

            return res.$accept();
        }

        function rejectEavrop(eavropId) {
            var EavropReject = $resource(RESTURL.eavropReject, {
                eavropId: '@eavropId'
            }, {
                'reject': {
                    method: 'PUT'
                }
            });

            var res = new EavropReject({
                eavropId: eavropId
            });

            return res.$reject();
        }

        function getEavropOrder(eavropId) {
            var resource = $resource(RESTURL.eavropOrder, {
                eavropId: '@eavropId'
            });

            return resource.get({
                eavropId: eavropId
            });
        }
    }
})();