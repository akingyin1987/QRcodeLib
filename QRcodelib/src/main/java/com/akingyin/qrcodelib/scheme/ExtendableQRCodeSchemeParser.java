package com.akingyin.qrcodelib.scheme;

import android.annotation.TargetApi;
import android.os.Build;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

/**
 * An implementation of {@link QRCodeSchemeParser} which supports the types
 * {@link Wifi}, {@link VCard}, {@link Girocode} and {@link URL}, and may be
 * extended by additional parsers for custom types. <br>
 * <br>
 * In order to add a parser, you have to put a properties file named
 * <code>qrcode.meta</code> into your <code>META-INF</code> folder, containing a
 * comma-separated list of your parser class names assigned to the key
 * <code>net.glxn.qrgen.core.scheme.QRCodeSchemeParser</code>. Let's say you
 * have additional parsers with the full qualified names
 * <code>org.me.FooSchemeParser</code> and <code>org.me.BarSchemeParser</code>,
 * your qrcode.meta must have the following entry.<br>
 * <br>
 * 
 * META-INF/qrcode.meta:
 * 
 * <pre>
 * net.glxn.qrgen.core.scheme.QRCodeSchemeParser=\
 * org.me.FooSchemeParser,\
 * org.me.BarSchemeParser
 * </pre>
 * 
 * TODO: does this parser stuff make sense at all?
 */
public class ExtendableQRCodeSchemeParser implements QRCodeSchemeParser {

	private Set<QRCodeSchemeParser> parser;

	@Override
	public Set<Class<?>> getSupportedSchemes() {
		Set<Class<?>> supportedSchemes = new LinkedHashSet<Class<?>>();
		for (QRCodeSchemeParser parser : getParser()) {
			supportedSchemes.addAll(parser.getSupportedSchemes());
		}
		return supportedSchemes;
	}

	public Object parse(final String qrCodeText)
			throws UnsupportedEncodingException {
		for (QRCodeSchemeParser parser : getParser()) {
			try {
				return parser.parse(qrCodeText);
			} catch (UnsupportedEncodingException e) {
				// go on
			}
		}
		throw new UnsupportedEncodingException("unkonwn QR code scheme: "
				+ qrCodeText);
	}

	protected Set<QRCodeSchemeParser> getParser() {
		if (parser == null) {
			parser = loadParser();
		}
		return parser;
	}


	protected Set<QRCodeSchemeParser> loadParser() {
		Set<QRCodeSchemeParser> result = new LinkedHashSet<QRCodeSchemeParser>();
		try {
			Enumeration<URL> resources = this.getClass().getClassLoader()
					.getResources("META-INF/qrcode.meta");
			for (URL url : Collections.list(resources)) {
				Properties properties = new Properties();

				try  {
					InputStream  is = url.openStream();
					properties.load(is);
					String prop = properties
							.getProperty(QRCodeSchemeParser.class.getName());
					String[] parserNames = prop.split(",");
					for (String className : parserNames) {
						result.add(createParserInstance(className));
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("failed to load schemes", e);
		}
		result.add(new QRCodeSchemeParserImpl());
		return result;
	}

	protected QRCodeSchemeParser createParserInstance(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Class<?> clazz = Class.forName(className.trim());
		return (QRCodeSchemeParser) clazz.newInstance();
	}

	static class QRCodeSchemeParserImpl implements QRCodeSchemeParser {

		@Override
		public Object parse(String qrCodeText)
				throws UnsupportedEncodingException {
			for (Class<?> type : getSupportedSchemes()) {
				Object instance = createInstance(qrCodeText, type);
				if (instance != null) {
					return instance;
				}
			}
			throw new UnsupportedEncodingException("unkonwn QR code scheme: "
					+ qrCodeText);
		}

		protected Object createInstance(final String qrCodeText,
				final Class<?> type) {
			try {
				if (VCard.class.equals(type)) {
					return VCard.parse(qrCodeText);
				}
				if (Girocode.class.equals(type)) {
					return Girocode.parse(qrCodeText);
				}
				if (Wifi.class.equals(type)) {
					return Wifi.parse(qrCodeText);
				}
				if (URL.class.equals(type)) {
					return new URL(qrCodeText);
				}
				return null;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public Set<Class<?>> getSupportedSchemes() {
			Set<Class<?>> supportedSchemes = new LinkedHashSet<Class<?>>();
			supportedSchemes.add(Girocode.class);
			supportedSchemes.add(VCard.class);
			supportedSchemes.add(Wifi.class);
			supportedSchemes.add(URL.class);
			return supportedSchemes;
		}

	}

}
