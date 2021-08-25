angular.module('app').controller('taskEditController',
        function($scope, $http, $routeParams){
            const contextPath = 'http://localhost:8180/app';

            $scope.fillTask = function(){

                function parsDate(strDate){
                    return moment(strDate, "yyyy-MM-dd HH:mm:ss").toDate;
                }

                $http({
                    url: contextPath + '/api/v1/tasks/' + $routeParams.taskId,
                    method: 'GET'
                })
                .then(function (response) {
                    $scope.task = response.data;
                    $scope.getProject($scope.task.projectId);
                });

            }

            $scope.getTaskStates = function(){
                $http({
                     url: contextPath + '/api/v1/taskstates',
                     method: 'GET'
                }).then(function (response) {
                    $scope.allTaskStates = response.data
                });
            }

            $scope.getProject = function(projectId){
                if(projectId>0){
                     $http({
                           url: contextPath + '/api/v1/projects/'+projectId,
                           method: 'GET'
                     }).then(function (response) {
                        $scope.project = response.data
                     });
                }else{
                    $scope.project = null;
                }
            }

            $scope.submitAddEditTask = function(){
                  alert('В разработке');
            }

            $scope.getTaskStates();
            $scope.fillTask();
        }
    );
