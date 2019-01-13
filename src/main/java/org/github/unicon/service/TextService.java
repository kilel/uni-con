package org.github.unicon.conv.text;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.text.StringEscapeUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.*;
import org.bouncycastle.util.BigIntegers;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Service
public class TextService {
    public String escape(String source, EscapeType type) {
        switch (type) {
            case URL:
                try {
                    return URLEncoder.encode(source, StandardCharsets.UTF_8.name());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            case XML:
                return StringEscapeUtils.escapeXml10(source);
            default:
                throw new IllegalArgumentException("Unknown escape type: " + type.getCode());
        }
    }

    public String unescape(String source, EscapeType type) {
        switch (type) {
            case URL:
                try {
                    return URLDecoder.decode(source, StandardCharsets.UTF_8.name());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            case XML:
                return StringEscapeUtils.unescapeXml(source);
            default:
                throw new IllegalArgumentException("Unknown escape type: " + type.getCode());
        }
    }

    public String encode(byte[] data, EncodingType type) {
        switch (type) {
            case PLAIN:
                return new String(data);
            case BIN:
                return new BigInteger(1, data).toString(2);
            case OCT:
                return new BigInteger(1, data).toString(8);
            case OCT_PER_BYTE:
                return encodePerByte(data, Integer::toOctalString);
            case DEC:
                return new BigInteger(1, data).toString(10);
            case DEC_PER_BYTE:
                return encodePerByte(data, String::valueOf);
            case HEX:
                return Hex.encodeHexString(data);
            case BASE32:
                return new Base32().encodeAsString(data);
            case BASE64:
                return Base64.getEncoder().encodeToString(data);
            default:
                throw new IllegalArgumentException("Unknown encoding type: " + type.getCode());
        }
    }


    public byte[] decode(String data, EncodingType type) {
        switch (type) {
            case PLAIN:
                return data.getBytes();
            case BIN:
                return BigIntegers.asUnsignedByteArray(new BigInteger(data, 2));
            case OCT:
                return BigIntegers.asUnsignedByteArray(new BigInteger(data, 8));
            case OCT_PER_BYTE:
                return decodePerByte(data, x -> Integer.parseInt(x, 8));
            case DEC:
                return BigIntegers.asUnsignedByteArray(new BigInteger(data, 10));
            case DEC_PER_BYTE:
                return decodePerByte(data, Integer::parseInt);
            case HEX:
                try {
                    return Hex.decodeHex(data);
                } catch (DecoderException e) {
                    throw new RuntimeException(e);
                }
            case BASE32:
                return new Base32().decode(data);
            case BASE64:
                return Base64.getDecoder().decode(data);
            default:
                throw new IllegalArgumentException("Unknown encoding type: " + type.getCode());
        }
    }


    private String encodePerByte(byte[] data, IntFunction<String> encoder) {
        int[] intData = toIntArray(data);
        return Arrays.stream(intData)
                .mapToObj(encoder)
                .collect(Collectors.joining(" "));
    }


    private byte[] decodePerByte(String data, ToIntFunction<String> decoder) {
        int[] intData = Arrays.stream(data.split(" "))
                .mapToInt(decoder)
                .toArray();
        return toByteArray(intData);
    }

    private int[] toIntArray(byte[] data) {
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = byteToInt(data[i]);
        }
        return result;
    }

    private byte[] toByteArray(int[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = intToByte(data[i]);
        }
        return result;
    }

    private byte intToByte(int src) {
        if (src < Byte.MAX_VALUE) {
            return (byte) src;
        } else {
            return (byte) (src - 256);
        }
    }

    private int byteToInt(byte src) {
        if (src >= 0) {
            return src;
        } else {
            return 256 + src;
        }
    }

    public String convert(String data, EncodingType source, EncodingType target) {
        return encode(decode(data, source), target);
    }

    public byte[] convert(byte[] data, EncodingType source, EncodingType target) {
        return decode(encode(data, source), target);
    }

    public byte[] hash(byte[] data, HashType type) {
        Digest digest;
        switch (type) {
            case MD5:
                digest = new MD5Digest();
                break;
            case SHA_1:
                digest = new SHA1Digest();
                break;
            case SHA_256:
                digest = new SHA256Digest();
                break;
            case SHA_512:
                digest = new SHA512Digest();
                break;
            case SHA_3:
                digest = new SHA3Digest();
                break;
            default:
                throw new IllegalArgumentException("Unknown hash type: " + type.getCode());
        }


        digest.update(data, 0, data.length);

        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);
        return result;
    }
}
