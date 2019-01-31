package org.github.unicon.conv.text;

import org.github.unicon.UniconApplicationTests;
import org.github.unicon.model.text.EscapeType;
import org.github.unicon.service.TextService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.github.unicon.model.text.EncodingType.*;
import static org.github.unicon.model.text.HashType.*;
import static org.junit.Assert.*;

public class TextServiceTest extends UniconApplicationTests {
    @Autowired
    private TextService textService;

    @Test
    public void escapeURLTest() {
        assertEquals("%7E%21%40%23%24%25%5E%26*%28%29_%2B-%3D%5B%5D%7B%7D%3B%27%3A%22%2C.%2F%3C%3E%3F",
                textService.escape("~!@#$%^&*()_+-=[]{};':\",./<>?", EscapeType.URL));
        assertEquals("text_dont_escape",
                textService.escape("text_dont_escape", EscapeType.URL));
    }

    @Test
    public void unescapeURLTest() {
        assertEquals("~!@#$%^&*()_+-=[]{};':\",./<>?",
                textService.unescape("%7E%21%40%23%24%25%5E%26*%28%29_%2B-%3D%5B%5D%7B%7D%3B%27%3A%22%2C.%2F%3C%3E%3F", EscapeType.URL));
        assertEquals("text_dont_escape",
                textService.unescape("text_dont_escape", EscapeType.URL));
    }

    @Test
    public void escapeXMLTest() {
        assertEquals("&apos;&lt;&gt;&amp;&quot;", textService.escape("'<>&\"", EscapeType.XML));
        assertEquals("some text, here!", textService.escape("some text, here!", EscapeType.XML));
        assertEquals("this &gt; &apos;that&quot; &amp; that &lt; this", textService.escape("this > 'that\" & that < this", EscapeType.XML));
    }

    @Test
    public void unescapeXMLTest() {
        assertEquals("'<>&\"", textService.unescape("&apos;&lt;&gt;&amp;&quot;", EscapeType.XML));
        assertEquals("some text, here!", textService.unescape("some text, here!", EscapeType.XML));
        assertEquals("this > 'that\" & that < this", textService.unescape("this &gt; &apos;that&quot; &amp; that &lt; this", EscapeType.XML));
    }

    @Test
    public void simpleEncodeTest() {
        String data = "some test text";
        assertEquals("ONXW2ZJAORSXG5BAORSXQ5A=",
                textService.convert(data, PLAIN, BASE32));
        assertEquals("c29tZSB0ZXN0IHRleHQ=",
                textService.convert(data, PLAIN, BASE64));
        assertEquals("111001101101111011011010110010100100000011101000110010101110011011101000010000001110100011001010111100001110100",
                textService.convert(data, PLAIN, BIN));
        assertEquals("7155733262440350625633502016431274164",
                textService.convert(data, PLAIN, OCT));
        assertEquals("2341305286579866607995536770693236",
                textService.convert(data, PLAIN, DEC));
        assertEquals("736f6d6520746573742074657874",
                textService.convert(data, PLAIN, HEX));
    }

    @Test
    public void notSimpleEncodeTest() {
            String data = "some test text абв";
        assertEquals("163 157 155 145 40 164 145 163 164 40 164 145 170 164 40 320 260 320 261 320 262",
                textService.convert(data, PLAIN, OCT_PER_BYTE));
        assertEquals("115 111 109 101 32 116 101 115 116 32 116 101 120 116 32 208 176 208 177 208 178",
                textService.convert(data, PLAIN, DEC_PER_BYTE));
        assertEquals(data,
                textService.convert("163 157 155 145 40 164 145 163 164 40 164 145 170 164 40 320 260 320 261 320 262", OCT_PER_BYTE, PLAIN));
        assertEquals(data,
                textService.convert("115 111 109 101 32 116 101 115 116 32 116 101 120 116 32 208 176 208 177 208 178", DEC_PER_BYTE, PLAIN));
    }

    @Test
    public void encodeTest() {
        String data = "111001101101111011011010110010100100000011101000110010101110011011101000010000001110100011001010111100001110100";
        String newData = textService.convert(data, BIN, DEC);
        assertEquals(data,
                textService.convert(newData, DEC, BIN));
    }

    @Test
    public void anotherEncodeTest() {
        String data = "1234567890-=~!@#$%^&*()_+";
        assertEquals("313233343536373839302d3d7e21402324255e262a28295f2b",
                textService.encode(textService.decode(data, PLAIN), HEX));

        String tmp = textService.convert(data, PLAIN, BASE32);
        String tmp2 = textService.convert(tmp, BASE32, BASE64);
        String result = textService.convert(tmp2, BASE64, HEX);
        assertEquals(textService.convert(data, PLAIN, HEX),
                result);
    }

    @Test
    public void anotherStupidTest() {
        String data = "87ublkj poy f ,liuhfk ;";

        String tmp = textService.convert(data, PLAIN, DEC);
        String tmp2 = textService.convert(tmp, DEC, OCT);
        String result = textService.convert(tmp2, OCT, DEC);
        assertEquals(tmp, result);
    }

    @Test (expected = NumberFormatException.class)
    public void binNegativeDecodeTest() {
        String data = "1100310101110010011100100110111101110010";

        textService.decode(data, BIN);
    }

    @Test (expected = NumberFormatException.class)
    public void octNegativeDecodeTest() {
        String data = "6856234467562";

        textService.decode(data, OCT);
    }

    @Test (expected = NumberFormatException.class)
    public void decNegativeDecodeTest() {
        String data = "4a5711799154";

        textService.decode(data, DEC);
    }

    @Test
    public void hexNegativeTest() {

        assertArrayEquals(
                textService.decode("6572726f72", HEX),
                textService.decode("6572726F72", HEX)
        );
    }

    @Test (expected = RuntimeException.class)
    public void hexAnotherNegativeTest() {
        String data = "6572726r72";

        textService.decode(data, HEX);
    }

    @Test (expected = NumberFormatException.class)
    public void octPerByteIncorrectNumberTest() {
        String data = "148 162 162 157 162";

        textService.decode(data, OCT_PER_BYTE);
    }

    @Test
    public void octPerByteNoSpaceTest() {
        String data = "145162 162 157 162";

        textService.decode(data, OCT_PER_BYTE);
    }

    @Test (expected = NumberFormatException.class)
    public void octPerByteTwoSpacesTest() {
        String data = "145  162 162 157 162";

        textService.decode(data, OCT_PER_BYTE);
    }

    @Test
    public void octPerByteNegativeTest() {
        String data = "-145 162 162 157 162";

        textService.decode(data, OCT_PER_BYTE);
    }

    @Test
    public void octPerByteBigNumberTest() {
        String data = "445 162 162 157 162";

        textService.decode(data, OCT_PER_BYTE);
    }

    @Test (expected = NumberFormatException.class)
    public void decPerByteIncorrectNumberTest() {
        String data = "1a1 114 114 111 114";

        textService.decode(data, DEC_PER_BYTE);
    }

    @Test
    public void decPerByteNoSpaceTest() {
        String data = "101114 114 111 114";

        textService.decode(data, DEC_PER_BYTE);
    }

    @Test (expected = NumberFormatException.class)
    public void decPerByteTwoSpacesTest() {
        String data = "101  114 114 111 114";

        textService.decode(data, DEC_PER_BYTE);
    }

    @Test
    public void decPerByteNegativeTest() {
        String data = "-101 114 114 111 114";

        textService.decode(data, DEC_PER_BYTE);
    }

    @Test
    public void decPerByteBigNumberTest() {
        String data = "301 114 114 111 114";

        textService.decode(data, DEC_PER_BYTE);
    }

    @Test
    public void base32IncorrectSymbolTest() {
        String data = "MVZHE03SOM======";

        textService.decode(data, BASE32);
    }

    @Test
    public void base32WrongPaddingTest() {
        String data = "MVZHE33SOM=====";

        textService.decode(data, BASE32);
    }

    @Test (expected = IllegalArgumentException.class)
    public void base64IncorrectSymbolTest() {
        String data = "ZXJ-b3I=";

        textService.decode(data, BASE64);
    }

    @Test
    public void base64WrongPaddingTest() {
        String data = "ZXJyb3I";

        assertEquals("error", textService.convert(data, BASE64, PLAIN));
    }

    @Test
    public void simpleHashTest() {
        String data = "serdgsrtysrthgbxcv cv r5t";
        assertEquals("f6340d9534a3b87669c7abc5106370b4",
                textService.encode(textService.hash(
                        textService.decode(data, PLAIN), MD5),
                        HEX));
        assertEquals("33aba27fb28df9c398d2b70be5ddb6647aa690cd",
                textService.encode(textService.hash(
                        textService.decode(data, PLAIN), SHA_1),
                        HEX));
        assertEquals("6343a4c895d14d16787b74d1c080f78b7da93663d05667b292a068de6b689bf0",
                textService.encode(textService.hash(
                        textService.decode(data, PLAIN), SHA_256),
                        HEX));
        assertEquals("51ad3d029937bd7d3ce098acd672ad7fa24696266c78faa97d7e70c6b819a5afe4b4aabc5e13ee95ad0209ee1d42a69315d858d11d2204009db9fe4dcd389d29",
                textService.encode(textService.hash(
                        textService.decode(data, PLAIN), SHA_512),
                        HEX));
        assertEquals("dbd2c783828473a7d5fb14c0284cde7e5cf722e196576ddaec4ae6c7deac991b",
                textService.encode(textService.hash(
                        textService.decode(data, PLAIN), SHA_3),
                        HEX));
        assertEquals("11110110001101000000110110010101001101001010001110111000011101100110100111000111101010111100010100010000011000110111000010110100",
                textService.encode(textService.hash(
                        textService.decode(data, PLAIN), MD5),
                        BIN));
    }

    @Test
    public void anotherHashTest() {
        byte[] data = textService.decode("serdgsrtysrthgbxcv cv r5t", PLAIN);
        byte[] hash = textService.hash(data, MD5);
        String resultHex = textService.encode(hash, HEX);
        String resultBase32 = textService.encode(hash, BASE32);
        String anotheResultHex = textService.convert(resultBase32, BASE32, HEX);

        assertEquals(resultHex, anotheResultHex);
    }
}
