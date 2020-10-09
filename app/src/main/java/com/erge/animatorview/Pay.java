package com.erge.animatorview;

/**
 * Created by mj on 2020/10/3 15:06
 */
class Pay {

    private Builder builder;

    private Pay(Builder builder) {
        this.builder = builder;
    }

    public void pay() {
        String payResult = "支付结果: \n" +
                "{\n" +
                "id: " +
                builder.params.id +
                "time: " +
                builder.params.time +
                "count: " +
                builder.params.count +
                "money: " +
                builder.params.money +
                "type: " +
                builder.params.type +
                "\n}";
        System.out.println(payResult);
    }

    private static class Params {
        private String id;
        private String time;
        private String count;
        private String money;
        private String type;
    }

    public static class Builder {
        private Params params;

        public Builder() {
            params = new Params();
            params.time = System.currentTimeMillis() + "";
            params.type = "alipay";
        }

        public Builder(Params params) {
            this.params = params;
        }


        public Builder id(String id) {
            params.id = id;
            return this;
        }
        public Builder time(String time) {
            params.time = time;
            return this;
        }
        public Builder count(String count) {
            params.id = count;
            return this;
        }
        public Builder money(String money) {
            params.id = money;
            return this;
        }
        public Builder type(String type) {
            params.type = type;
            return this;
        }

        public Pay build() {
            return new Pay(this);
        }
    }

    public static void main(String[] args) {
        Pay pay = new Pay.Builder()
                .id("789")
                .money("100")
                .count("3")
                .build();
        pay.pay();
    }

}
