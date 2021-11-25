let kakao = null;

export default class KakaoMapUtils {
    /**
     * @author Lee Jae Yeol
     * @param dom : HTMLElement - 맵 DOM
     * @description Kakao Map 초기화
     * */
    constructor(dom) {
        this.map = null;
        this.markers = null;

        const mapInit = async () => {
            kakao = window.kakao;
            this.markers = [];
            this.map = new kakao.maps.Map(dom, {
                center: new kakao.maps.LatLng(36.020826, 127.0236867),
                level: 12
            });
            this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN);

            await new Promise(resolve => setTimeout(resolve, 0));
            this.map.relayout();
            await new Promise(resolve => setTimeout(resolve, 10));
            this.map.setCenter(new kakao.maps.LatLng(36.020826, 127.0236867));
        };

        window.kakao && window.kakao.maps ? mapInit() : (() => {
            kakao = window.kakao;

            const script = document.createElement("script");
            script.onload = () => kakao.maps.load(mapInit);
            script.src = "http://dapi.kakao.com/v2/maps/sdk.js?appkey=70577d992680982a29613694fba5407f&libraries=services,clusterer,drawing";
            document.head.appendChild(script);
        })();
    }

    /**
     * @author Lee Jae Yeol
     * @param id : string - 맵 타입
     * - ROADMAP : 일반지도
     * - SKYVIEW : 스카이뷰(위성지도)
     * - HYBRID : 하이브리드(스카이뷰 + 레이블)
     * - ROADVIEW : 로드뷰
     * - OVERLAY : 레이블
     * - TRAFFIC : 교통정보
     * - TERRAIN : 지형도
     * - BICYCLE : 자전거
     * - BICYCLE_HYBRID : 스카이뷰를 위한 자전거 (어두운 지도에서 활용)
     * - USE_DISTRICT : 지적편집도
     * */
    setMapType(id) {
        this.map.setMapTypeId(kakao.maps.MapTypeId[id]);
    }

    /**
     * @author Lee Jae Yeol
     * @param latitude : number - 위도
     * @param longitude : number - 경도
     * @param content : string - 마커 툴팁 내용에 들어갈 HTML Markup Text
     * @param markerImgUrl : string - 마커 이미지 URL
     * */
    addMarker(latitude, longitude, content, markerImgUrl) {
        const marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(latitude, longitude),
            image: new kakao.maps.MarkerImage(markerImgUrl, new kakao.maps.Size(18, 18))
        });

        let infoWindow = new kakao.maps.InfoWindow({ content });

        kakao.maps.event.addListener(marker, "mouseover", () => infoWindow.open(this.map, marker));
        kakao.maps.event.addListener(marker, "mouseout", () => infoWindow.close());

        this.markers.push({ marker, infoWindow });
    }

    /**
     * @author Lee Jae Yeol
     * @param data : { latitude: number, longitude: number }[] - 위/경도 데이터 리스트
     * */
    clustering(data) {
        if (this.clusterer != null) this.clusterer.clear();
        this.clusterer = new kakao.maps.MarkerClusterer({
            map: this.map,
            averageCenter: true,
            minLevel: 10
        });
        this.clusterer.addMarkers(data.map(obj => new kakao.maps.Marker({ position: new kakao.maps.LatLng(obj.latitude, obj.longitude) })));
    }

    setOverlay(callback) {
        const markers = this.clusterer._markers;
        markers.forEach(marker => {
            const position = marker.getPosition();
            const [latitude, longitude] = [position.Ma, position.La];
            let content = document.createElement("div");
            content.innerHTML = typeof callback === "function" ? callback(latitude, longitude) : callback.toString();
            content = content.querySelector("div.overlay");

            const overlay = new kakao.maps.CustomOverlay({
                map: this.map,
                content,
                position: new kakao.maps.LatLng(latitude, longitude),
                zIndex: 999
            });
            overlay.setVisible(false);

            const parent = overlay.getContent().parentElement;
            parent.classList.add("overlay-parent");

            kakao.maps.event.addListener(marker, "mouseover", () => overlay.setVisible(true));
            kakao.maps.event.addListener(marker, "mouseout", () => overlay.setVisible(false));
            content.parentElement.onmouseleave = () => overlay.setVisible(false);
        });
    }

    addMarkerEventListener(eventListener, callback) {
        this.clusterer._markers.forEach(marker => kakao.maps.event.addListener(marker, eventListener, () => {
            const position = marker.getPosition();
            callback(position.Ma, position.La);
        }));
    }
}
