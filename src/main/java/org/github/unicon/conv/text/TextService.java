package org.github.unicon.conv.text;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.text.StringEscapeUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.*;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
                return new BigInteger(data).toString(2);
            case OCT:
                return new BigInteger(data).toString(8);
            case DEC:
                return new BigInteger(data).toString(10);
            case HEX:
                return new BigInteger(data).toString(16);
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
                return new BigInteger(data, 2).toByteArray();
            case OCT:
                return new BigInteger(data, 8).toByteArray();
            case DEC:
                return new BigInteger(data, 10).toByteArray();
            case HEX:
                return new BigInteger(data, 16).toByteArray();
            case BASE32:
                return new Base32().decode(data);
            case BASE64:
                return Base64.getDecoder().decode(data);
            default:
                throw new IllegalArgumentException("Unknown encoding type: " + type.getCode());
        }
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
