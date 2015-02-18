(function() {
    'use strict';

    angular
        .module('fmu.core')
        .factory('Dataservice', dataservice);

    dataservice.$inject = ['$resource', 'RESTURL', 'logger', 'EAVROP_STATES'];

    function dataservice($resource, RESTURL, logger, EAVROP_STATES) {
        var dataResources = {};
        var service = {
            resources: dataResources,
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
            getEavropOrder: getEavropOrder,
            getRecievedDocuments: getEavropRecievedDocuments,
            saveRecievedDocuments: saveRecievedDocuments,
            getRequestedDocuments: getRequestedDocuments,
            saverequestedDocuments: saverequestedDocuments,
            getNotes: getNotes,
            addNote: addNote,
            removeNote: removeNote,
            getAllEvents: getAllEvents,
            getCompensation: getCompensation
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

            return resource.get({
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

        function getRecievedDocumentsResource() {
            if (!dataResources.EavropRecievedDocuments) {
                dataResources.EavropRecievedDocuments = $resource(RESTURL.eavropDocuments, {
                    eavropId: '@eavropId'
                });
            }

            return dataResources.EavropRecievedDocuments;
        }

        function getEavropRecievedDocuments(eavropId) {
            var Resource = getRecievedDocumentsResource();

            return Resource.query({
                eavropId: eavropId
            });
        }

        function saveRecievedDocuments(eavropId, postdata) {
            var Resource = getRecievedDocumentsResource();

            var saveResource = new Resource(postdata);
            return saveResource.$save({
                eavropId: eavropId
            });
        }

        function getRequestedDocumentsResource() {
            if (!dataResources.RequestedDocuments) {
                dataResources.RequestedDocuments = $resource(RESTURL.eavropRequestedDocuments, {
                    eavropId: '@eavropId'
                });
            }

            return dataResources.RequestedDocuments;
        }

        function getRequestedDocuments(eavropId) {
            var resource = getRequestedDocumentsResource();
            return resource.query({
                eavropId: eavropId
            });
        }

        function saverequestedDocuments(eavropId, postdata) {
            var Resource = getRequestedDocumentsResource();
            return new Resource(postdata).$save({
                eavropId: eavropId
            });
        }

        function getNoteResource() {
            if (!dataResources.Notes) {
                dataResources.Notes = $resource(RESTURL.eavropNotes, {
                    eavropId: '@eavropId'
                });
            }

            return dataResources.Notes;
        }

        function getNotes(eavropId) {
            var Resource = getNoteResource();

            return Resource.query({
                eavropId: eavropId
            });
        }

        function addNote(eavropId, noteData) {
            var Note = $resource(RESTURL.eavropAddNote);
            var newNote = new Note(noteData);
            return newNote.$save();
        }

        function removeNote(eavropId, noteId) {
            var Note = $resource(RESTURL.eavropRemoveNote, {
                eavropId: '@eavropId',
                noteId: '@noteId'
            });

            return Note.remove({
                eavropId: eavropId,
                noteId: noteId
            });
        }

        function getAllEvents(eavropId) {
            var Resource = $resource(RESTURL.eavropAllEvents, {
                eavropId: '@eavropId'
            });

            return Resource.get({
                eavropId: eavropId
            });
        }

        function getCompensation(eavropId) {
            var Resource = $resource(RESTURL.eavropCompensation, {
                eavropId: '@eavropId'
            });

            return Resource.get({
                eavropId: eavropId
            });
        }
    }
})();