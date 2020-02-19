Vue.component('quota-table', {
    template: `
    <div class="col-sm-12">
        <button class="btn btn-default" style="width: 10%; margin-right: 4%; margin-bottom: 10px;" v-for="(w,index) in weeks">
            {{w.datetime}}<br/>
           {{w.value}}
    
        </button>
        <table   border="1px" class="table table-bordered" style="text-align: center;vertical-align:middle;" v-for="item in quota.bookItems">
            <tr>
                <td style="padding: 8px">项目名称</td>
                <td style="padding: 8px">病人来源</td>
                <td style="padding: 8px" v-for="slice in item.timeSlices" >{{slice.startTime | formatSlice}} - {{slice.endTime | formatSlice}}</td>
            </tr>
            <tr >
                <td style="padding: 8px"  :rowspan="item.quotaCategories.length + 2">{{item.bookItemName}}</td>
            </tr>
            <tr v-for="qc in item.quotaCategories">
                <td style="padding: 8px">{{qc.name}}</td>
                <td style="padding: 8px" v-for="slice in item.timeSlices">
                    {{item.map[weekId + '-' + slice.timeSliceId + '-' + qc.quotaCategoryId]}}       
                </td>
            </tr>
        </table>
    </div>`,
    data: function () {
        return {
            quota: {},
            weeks: [],
            weekId: "",

        }
    },
    props: ['detection'],
    filters: {
        formatSlice: function (str) {
            return str.substring(0, 5);
        }
    },
    created: function () {
        this.init();
    },
    methods: {
        init: function () {
            this.getWeeks();
            this.getQuota(this.detection, 4);

        },
        getQuota: function (detectionDepId, weekId) {
            var vm = this;
            $.get(baseURL + "book/quota/info?detectionId=" + detectionDepId + "&weekId=" + weekId, function (r) {
                vm.quota = r.quota;
            })
        },
        getWeeks: function () {
            var vm = this;
            $.get(baseURL + "sys/dict/week", function (r) {
                const dateOfToday = Date.now();
                const dayOfToday = (new Date().getDay() + 7 - 1) % 7;
                const daysOfThisWeek = Array.from(new Array(7))
                    .map((_, i) => {
                        const date = new Date(dateOfToday + (i - dayOfToday) * 1000 * 60 * 60 * 24);
                        return date.getFullYear() +
                            '-' +
                            String(date.getMonth() + 1).padStart(2, '0') +
                            '-' +
                            String(date.getDate()).padStart(2, '0')
                    });
                var datetime = 'datetime';
                for (var i = 0; i < 7; i++) {
                    r.week[i][datetime] = daysOfThisWeek[i];
                }
                vm.weeks = r.week;
                vm.weekId = r.week[0].id;
            });
        },


    }
});