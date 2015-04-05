package interfaces;

public interface CountryNameToAlpha2Converter {
    ConversionResult convert(String countryName);

    class ConversionResult {
        private String countryNameQuery;
        private String countryNameAnswer;
        private String alpha2Code;
        private ConversionResultStatus status;

        public ConversionResult(String countryNameQuery, String countryNameAnswer, String alpha2Code, ConversionResultStatus status) {
            this.countryNameQuery = countryNameQuery;
            this.countryNameAnswer = countryNameAnswer;
            this.alpha2Code = alpha2Code;
            this.status = status;
        }

        public ConversionResultStatus getStatus() {
            return status;
        }

        public String getCountryNameAnswer() {
            return countryNameAnswer;
        }

        public String getCountryNameQuery() {
            return countryNameQuery;
        }

        public String getAlpha2Code() {
            return alpha2Code;
        }

        public enum ConversionResultStatus {
            OK,
            WARN,
            ERROR
        }
    }
}
