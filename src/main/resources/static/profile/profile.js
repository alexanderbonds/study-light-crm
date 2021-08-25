angular.module('app').controller('profileController',
    function ($scope, $http, $localStorage, $mdDialog) {
        const contextPath = 'http://localhost:8180/app';

        $scope.uploadFile = function (element) {
            if (element.files[0] != null) {
                const payload = new FormData();
                payload.append('attachment', element.files[0]);
                $http({
                    url: contextPath + '/api/v1/files/photo',
                    method: 'POST',
                    data: payload,
                    headers: {
                        'Content-Type': undefined,
                    },
                    transformRequest: angular.identity
                })
                    .then(function successCallback(response) {
                        // alert('Фото успешно загружено на сервер');
                        $scope.getPreview();
                    }, function errorCallback(response) {
                        // window.alert(response.data.msg);
                        $scope.showAlert(response.data.msg);
                    });
            }
        };

        $scope.getPreview = function () {
            if ($scope.img == null) {
                $http.get(contextPath + '/api/v1/files/photo/preview', {responseType: "arraybuffer"}
                )
                    .then(function (response) {
                        let contentType = response.headers("content-type");
                        let blob = new Blob([response.data], {type: contentType});
                        $scope.img = (window.URL || window.webkitURL).createObjectURL(blob);
                    });
            }
        };

        $scope.getPhoto = function () {
            if ($scope.img != null && $scope.photoUrl == null) {
                $http.get(contextPath + '/api/v1/files/photo', {responseType: "arraybuffer"}
                )
                    .then(function (response) {
                        let contentType = response.headers("content-type");
                        let blob = new Blob([response.data], {type: contentType});
                        $scope.photoUrl = (window.URL || window.webkitURL).createObjectURL(blob);
                        window.open($scope.photoUrl);
                    });
            } else if ($scope.photoUrl != null) {
                window.open($scope.photoUrl);
            }
        };

        $scope.showAlert = function (message) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('Предупреждение')
                    .textContent(message)
                    .ok('ОК')
            );
        };

        $scope.getPreview();

    });
