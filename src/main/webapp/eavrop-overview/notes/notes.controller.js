(function() {
    'use strict';
    angular.module('fmu.eavrop')
        .controller('NotesController', NotesController);

    NotesController.$inject = ['$scope', '$modal', '$filter', '$stateParams', 'Dataservice', 'eavropService', 'gettext'];

    function NotesController($scope, $modal, $filter, $stateParams, Dataservice, eavropService, gettext) {
        var EAVROP_NOTES = {
            cannotAdd: gettext('Anteckningar/Anteckningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda'),
            cannotRemove: gettext('Anteckningar/Fel uppstod vid bortagning av anteckning, detta kan beror på att du inte har rätt behörighet att genomföra denna förfrågan')
        };
        $scope.openRemoveNote = openRemoveNoteFn;
        $scope.openAddNoteModal = openAddNoteModalFn;

        loadNotes();


        function openRemoveNoteFn(noteData) {
            $modal.open({
                templateUrl: 'eavrop-overview/notes/confirmRemoveModal.html',
                size: 'md',
                resolve: {
                    noteData: function() {
                        return noteData;
                    }
                },
                controller: function($scope, noteData, $modalInstance) {
                    $scope.cancelRemoval = cancelRemovalFn;
                    $scope.removeNote = removeNoteFn;

                    function removeNoteFn() {
                        if (noteData && noteData.removable) {
                            var promise = Dataservice.removeNote($stateParams.eavropId, noteData.noteId).$promise;
                            promise.then(function() {
                                $modalInstance.close();
                                loadNotes();
                            }, function() {
                                $scope.noteError = [EAVROP_NOTES.cannotRemove];
                            });
                        }
                    }
                    function cancelRemovalFn() {
                        $modalInstance.close();
                    }
                }
            });
        }

        function loadNotes() {
            $scope.notes = Dataservice.getNotes($stateParams.eavropId);
        }

        function openAddNoteModalFn() {
            $modal.open({
                templateUrl: 'eavrop-overview/notes/add-note-modal.html',
                size: 'md',
                controller: function($scope, $modalInstance) {
                    $scope.picker = {
                        opened: false
                    };
                    $scope.note = {
                        content: '',
                        createdDate: new Date()
                    };
                    $scope.open = openFn;
                    $scope.save = saveFn;
                    $scope.close = closeFn;


                    function openFn($event) {
                        $event.preventDefault();
                        $event.stopPropagation();
                        $scope.picker.opened = true;
                    }
                    function createNoteDateObject() {
                        return {
                            eavropId: $stateParams.eavropId,
                            text: $scope.note.content
                        };
                    }
                    function saveFn() {
                        var promise = Dataservice.addNote($stateParams.eavropId, createNoteDateObject());
                        promise.then(function() {
                            // Success
                            $modalInstance.close();
                            loadNotes();
                        }, function() {
                            // Failed
                            $scope.noteError = [EAVROP_NOTES.cannotAdd];
                        });
                    }
                    function closeFn() {
                        $modalInstance.dismiss();
                    }
                }
            });
        }
    }
})();