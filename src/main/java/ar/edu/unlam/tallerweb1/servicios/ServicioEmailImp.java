package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Compra;
import ar.edu.unlam.tallerweb1.modelo.Publicacion;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service("servicioEmail")
public class ServicioEmailImp implements ServicioEmail {

	private ServicioPublicacion servicioPublicacion;
	private  JavaMailSender mailSender;

	@Autowired
	private ServicioEmailImp(JavaMailSender mailSender, ServicioPublicacion servicioPublicacion){
		this.mailSender = mailSender;
		this.servicioPublicacion = servicioPublicacion;
	}

	@Override
	public void enviarEmail(String destino, String asunto, String texto, String image, String pdf) throws MessagingException {
		MimeMessage mensaje  = mailSender.createMimeMessage();
		MimeMessageHelper email = new MimeMessageHelper(mensaje, true, "UTF-8");
		email.setTo(destino);
		email.setSubject(asunto);
		email.setText(texto, true);
		email.addInline("Image", new File(image));
		if(pdf != null){
			email.addAttachment(new File(pdf).getName(), new File(pdf));
		}
		mailSender.send(mensaje);
	}

	@Override
	public void enviarEmailComprador(Long publicacionId, Usuario usuario, String url) throws MessagingException, IOException {
		String email = usuario.getEmail();
		Publicacion publicacion = this.servicioPublicacion.buscarPublicacionPorId(publicacionId);
		String imagen = url.substring(0, url.length() -1) + publicacion.getLibro().getImagen();
		String pdf = url.substring(0, url.length() -1) + publicacion.getLibro().getRuta();
		String asunto = "Compraste " + publicacion.getLibro().getNombre();
		String nuevaLinea = System.getProperty("line.separator");
		String texto = "<!doctype html>\n" +
				"<html>\n" +
				"  <head>\n" +
				"    <meta name=\"viewport\" content=\"width=device-width\">\n" +
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
				"    <title>Simple Transactional Email</title>\n" +
				"    <style>\n" +
				"    /* -------------------------------------\n" +
				"        INLINED WITH htmlemail.io/inline\n" +
				"    ------------------------------------- */\n" +
				"    /* -------------------------------------\n" +
				"        RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
				"    ------------------------------------- */\n" +
				"    @media only screen and (max-width: 620px) {\n" +
				"      table[class=body] h1 {\n" +
				"        font-size: 28px !important;\n" +
				"        margin-bottom: 10px !important;\n" +
				"      }\n" +
				"      table[class=body] p,\n" +
				"            table[class=body] ul,\n" +
				"            table[class=body] ol,\n" +
				"            table[class=body] td,\n" +
				"            table[class=body] span,\n" +
				"            table[class=body] a {\n" +
				"        font-size: 16px !important;\n" +
				"      }\n" +
				"      table[class=body] .wrapper,\n" +
				"            table[class=body] .article {\n" +
				"        padding: 10px !important;\n" +
				"      }\n" +
				"      table[class=body] .content {\n" +
				"        padding: 0 !important;\n" +
				"      }\n" +
				"      table[class=body] .container {\n" +
				"        padding: 0 !important;\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .main {\n" +
				"        border-left-width: 0 !important;\n" +
				"        border-radius: 0 !important;\n" +
				"        border-right-width: 0 !important;\n" +
				"      }\n" +
				"      table[class=body] .btn table {\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .btn a {\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .img-responsive {\n" +
				"        height: auto !important;\n" +
				"        max-width: 100% !important;\n" +
				"        width: auto !important;\n" +
				"      }\n" +
				"    }\n" +
				"\n" +
				"    /* -------------------------------------\n" +
				"        PRESERVE THESE STYLES IN THE HEAD\n" +
				"    ------------------------------------- */\n" +
				"    @media all {\n" +
				"      .ExternalClass {\n" +
				"        width: 100%;\n" +
				"      }\n" +
				"      .ExternalClass,\n" +
				"            .ExternalClass p,\n" +
				"            .ExternalClass span,\n" +
				"            .ExternalClass font,\n" +
				"            .ExternalClass td,\n" +
				"            .ExternalClass div {\n" +
				"        line-height: 100%;\n" +
				"      }\n" +
				"      .apple-link a {\n" +
				"        color: inherit !important;\n" +
				"        font-family: inherit !important;\n" +
				"        font-size: inherit !important;\n" +
				"        font-weight: inherit !important;\n" +
				"        line-height: inherit !important;\n" +
				"        text-decoration: none !important;\n" +
				"      }\n" +
				"      #MessageViewBody a {\n" +
				"        color: inherit;\n" +
				"        text-decoration: none;\n" +
				"        font-size: inherit;\n" +
				"        font-family: inherit;\n" +
				"        font-weight: inherit;\n" +
				"        line-height: inherit;\n" +
				"      }\n" +
				"      .btn-primary table td:hover {\n" +
				"        background-color: #34495e !important;\n" +
				"      }\n" +
				"      .btn-primary a:hover {\n" +
				"        background-color: #34495e !important;\n" +
				"        border-color: #34495e !important;\n" +
				"      }\n" +
				"    }\n" +
				"    </style>\n" +
				"  </head>\n" +
				"  <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
				"    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n" +
				"      <tr>\n" +
				"        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
				"        <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n" +
				"          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n" +
				"\n" +
				"            <!-- START CENTERED WHITE CONTAINER -->\n" +
				"            <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\">Compra de libro.</span>\n" +
				"            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">\n" +
				"\n" +
				"              <!-- START MAIN CONTENT AREA -->\n" +
				"              <tr>\n" +
				"                <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\n" +
				"                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
				"                    <tr>\n" +
				"                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
				"                        <h3 style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Resumen de compra</h3>\n" +
				"                        <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">" + publicacion.getLibro().getNombre() + "</p>\n" +
				"                        <img src=\"cid:Image\"  width=\"50%\" height=\"50%\"/>" +
				"                        <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Pagaste $" + publicacion.getPrecio().toString() + "</p>\n" +
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\n" +
				"                          <tbody>\n" +
				"                            <tr>\n" +
				"                              <td align=\"left\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\">\n" +
				"                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">\n" +
				"                                  <tbody>\n" +
				"                                    <tr>\n" +
				"                                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a href=\"http://localhost:8080/historial-de-transacciones\" target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">Ver ventas</a> </td>\n" +
				"                                    </tr>\n" +
				"                                  </tbody>\n" +
				"                                </table>\n" +
				"                              </td>\n" +
				"                            </tr>\n" +
				"                          </tbody>\n" +
				"                        </table>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                  </table>\n" +
				"                </td>\n" +
				"              </tr>\n" +
				"\n" +
				"            <!-- END MAIN CONTENT AREA -->\n" +
				"            </table>\n" +
				"\n" +
				"            <!-- START FOOTER -->\n" +
				"            <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">\n" +
				"              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
				"                <tr>\n" +
				"                  <td class=\"content-block\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
				"                  </td>\n" +
				"                </tr>\n" +
				"                <tr>\n" +
				"                  <td class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
				"                    Powered by <a href=\"http://localhost:8080\" style=\"color: #999999; font-size: 12px; text-align: center; text-decoration: none;\">404 NOT FOUND</a>.\n" +
				"                  </td>\n" +
				"                </tr>\n" +
				"              </table>\n" +
				"            </div>\n" +
				"            <!-- END FOOTER -->\n" +
				"\n" +
				"          <!-- END CENTERED WHITE CONTAINER -->\n" +
				"          </div>\n" +
				"        </td>\n" +
				"        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
				"      </tr>\n" +
				"    </table>\n" +
				"  </body>\n" +
				"</html>";
		this.enviarEmail(email, asunto, texto, imagen, null);
	}

	@Override
	public void enviarEmailVendedor(Long publicacionId, String url) throws MessagingException, IOException {
		Publicacion publicacion = this.servicioPublicacion.buscarPublicacionPorId(publicacionId);
		String vendedor = publicacion.getPropietario().getEmail();
		String imagen = url.substring(0, url.length() -1) + publicacion.getLibro().getImagen();
		String pdf = url.substring(0, url.length() -1) + publicacion.getLibro().getRuta();
		String asunto_venta = "Compraron tu libro " + publicacion.getLibro().getNombre();
		String texto_venta = "<!doctype html>\n" +
				"<html>\n" +
				"  <head>\n" +
				"    <meta name=\"viewport\" content=\"width=device-width\">\n" +
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
				"    <title>Simple Transactional Email</title>\n" +
				"    <style>\n" +
				"    /* -------------------------------------\n" +
				"        INLINED WITH htmlemail.io/inline\n" +
				"    ------------------------------------- */\n" +
				"    /* -------------------------------------\n" +
				"        RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
				"    ------------------------------------- */\n" +
				"    @media only screen and (max-width: 620px) {\n" +
				"      table[class=body] h1 {\n" +
				"        font-size: 28px !important;\n" +
				"        margin-bottom: 10px !important;\n" +
				"      }\n" +
				"      table[class=body] p,\n" +
				"            table[class=body] ul,\n" +
				"            table[class=body] ol,\n" +
				"            table[class=body] td,\n" +
				"            table[class=body] span,\n" +
				"            table[class=body] a {\n" +
				"        font-size: 16px !important;\n" +
				"      }\n" +
				"      table[class=body] .wrapper,\n" +
				"            table[class=body] .article {\n" +
				"        padding: 10px !important;\n" +
				"      }\n" +
				"      table[class=body] .content {\n" +
				"        padding: 0 !important;\n" +
				"      }\n" +
				"      table[class=body] .container {\n" +
				"        padding: 0 !important;\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .main {\n" +
				"        border-left-width: 0 !important;\n" +
				"        border-radius: 0 !important;\n" +
				"        border-right-width: 0 !important;\n" +
				"      }\n" +
				"      table[class=body] .btn table {\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .btn a {\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .img-responsive {\n" +
				"        height: auto !important;\n" +
				"        max-width: 100% !important;\n" +
				"        width: auto !important;\n" +
				"      }\n" +
				"    }\n" +
				"\n" +
				"    /* -------------------------------------\n" +
				"        PRESERVE THESE STYLES IN THE HEAD\n" +
				"    ------------------------------------- */\n" +
				"    @media all {\n" +
				"      .ExternalClass {\n" +
				"        width: 100%;\n" +
				"      }\n" +
				"      .ExternalClass,\n" +
				"            .ExternalClass p,\n" +
				"            .ExternalClass span,\n" +
				"            .ExternalClass font,\n" +
				"            .ExternalClass td,\n" +
				"            .ExternalClass div {\n" +
				"        line-height: 100%;\n" +
				"      }\n" +
				"      .apple-link a {\n" +
				"        color: inherit !important;\n" +
				"        font-family: inherit !important;\n" +
				"        font-size: inherit !important;\n" +
				"        font-weight: inherit !important;\n" +
				"        line-height: inherit !important;\n" +
				"        text-decoration: none !important;\n" +
				"      }\n" +
				"      #MessageViewBody a {\n" +
				"        color: inherit;\n" +
				"        text-decoration: none;\n" +
				"        font-size: inherit;\n" +
				"        font-family: inherit;\n" +
				"        font-weight: inherit;\n" +
				"        line-height: inherit;\n" +
				"      }\n" +
				"      .btn-primary table td:hover {\n" +
				"        background-color: #34495e !important;\n" +
				"      }\n" +
				"      .btn-primary a:hover {\n" +
				"        background-color: #34495e !important;\n" +
				"        border-color: #34495e !important;\n" +
				"      }\n" +
				"    }\n" +
				"    </style>\n" +
				"  </head>\n" +
				"  <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
				"    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n" +
				"      <tr>\n" +
				"        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
				"        <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n" +
				"          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n" +
				"\n" +
				"            <!-- START CENTERED WHITE CONTAINER -->\n" +
				"            <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\">Venta de libro.</span>\n" +
				"            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">\n" +
				"\n" +
				"              <!-- START MAIN CONTENT AREA -->\n" +
				"              <tr>\n" +
				"                <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\n" +
				"                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
				"                    <tr>\n" +
				"                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
				"                        <h3 style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Venta de libro</h3>\n" +
				"                        <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">" + publicacion.getLibro().getNombre() + "</p>\n" +
				"						<img src=\"cid:Image\"  width=\"50%\" height=\"50%\"/>" +
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\n" +
				"                          <tbody>\n" +
				"                            <tr>\n" +
				"                              <td align=\"left\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\">\n" +
				"                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">\n" +
				"                                  <tbody>\n" +
				"                                    <tr>\n" +
				"                                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a href=\"http://localhost:8080/biblioteca\" target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">Ver ventas</a> </td>\n" +
				"                                    </tr>\n" +
				"                                  </tbody>\n" +
				"                                </table>\n" +
				"                              </td>\n" +
				"                            </tr>\n" +
				"                          </tbody>\n" +
				"                        </table>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                  </table>\n" +
				"                </td>\n" +
				"              </tr>\n" +
				"\n" +
				"            <!-- END MAIN CONTENT AREA -->\n" +
				"            </table>\n" +
				"\n" +
				"            <!-- START FOOTER -->\n" +
				"            <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">\n" +
				"              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
				"                <tr>\n" +
				"                  <td class=\"content-block\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
				"                  </td>\n" +
				"                </tr>\n" +
				"                <tr>\n" +
				"                  <td class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
				"                    Powered by <a href=\"http://localhost:8080\" style=\"color: #999999; font-size: 12px; text-align: center; text-decoration: none;\">404 NOT FOUND</a>.\n" +
				"                  </td>\n" +
				"                </tr>\n" +
				"              </table>\n" +
				"            </div>\n" +
				"            <!-- END FOOTER -->\n" +
				"\n" +
				"          <!-- END CENTERED WHITE CONTAINER -->\n" +
				"          </div>\n" +
				"        </td>\n" +
				"        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
				"      </tr>\n" +
				"    </table>\n" +
				"  </body>\n" +
				"</html>";
				this.enviarEmail(vendedor, asunto_venta, texto_venta, imagen, null);
	}

	@Override
	public void enviarEmailRegalo(Long publicacionId, Usuario usuario, String url) throws MessagingException, IOException {
		Publicacion publicacion = this.servicioPublicacion.buscarPublicacionPorId(publicacionId);
		String beneficiado = usuario.getEmail();
		String imagen = url.substring(0, url.length() -1) + publicacion.getLibro().getImagen();
		String pdf = url.substring(0, url.length() -1) + publicacion.getLibro().getRuta();
		String asunto = "Te regalaron el libro " + publicacion.getLibro().getNombre();
		String texto = "<!doctype html>\n" +
				"<html>\n" +
				"  <head>\n" +
				"    <meta name=\"viewport\" content=\"width=device-width\">\n" +
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
				"    <title>Simple Transactional Email</title>\n" +
				"    <style>\n" +
				"    /* -------------------------------------\n" +
				"        INLINED WITH htmlemail.io/inline\n" +
				"    ------------------------------------- */\n" +
				"    /* -------------------------------------\n" +
				"        RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
				"    ------------------------------------- */\n" +
				"    @media only screen and (max-width: 620px) {\n" +
				"      table[class=body] h1 {\n" +
				"        font-size: 28px !important;\n" +
				"        margin-bottom: 10px !important;\n" +
				"      }\n" +
				"      table[class=body] p,\n" +
				"            table[class=body] ul,\n" +
				"            table[class=body] ol,\n" +
				"            table[class=body] td,\n" +
				"            table[class=body] span,\n" +
				"            table[class=body] a {\n" +
				"        font-size: 16px !important;\n" +
				"      }\n" +
				"      table[class=body] .wrapper,\n" +
				"            table[class=body] .article {\n" +
				"        padding: 10px !important;\n" +
				"      }\n" +
				"      table[class=body] .content {\n" +
				"        padding: 0 !important;\n" +
				"      }\n" +
				"      table[class=body] .container {\n" +
				"        padding: 0 !important;\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .main {\n" +
				"        border-left-width: 0 !important;\n" +
				"        border-radius: 0 !important;\n" +
				"        border-right-width: 0 !important;\n" +
				"      }\n" +
				"      table[class=body] .btn table {\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .btn a {\n" +
				"        width: 100% !important;\n" +
				"      }\n" +
				"      table[class=body] .img-responsive {\n" +
				"        height: auto !important;\n" +
				"        max-width: 100% !important;\n" +
				"        width: auto !important;\n" +
				"      }\n" +
				"    }\n" +
				"\n" +
				"    /* -------------------------------------\n" +
				"        PRESERVE THESE STYLES IN THE HEAD\n" +
				"    ------------------------------------- */\n" +
				"    @media all {\n" +
				"      .ExternalClass {\n" +
				"        width: 100%;\n" +
				"      }\n" +
				"      .ExternalClass,\n" +
				"            .ExternalClass p,\n" +
				"            .ExternalClass span,\n" +
				"            .ExternalClass font,\n" +
				"            .ExternalClass td,\n" +
				"            .ExternalClass div {\n" +
				"        line-height: 100%;\n" +
				"      }\n" +
				"      .apple-link a {\n" +
				"        color: inherit !important;\n" +
				"        font-family: inherit !important;\n" +
				"        font-size: inherit !important;\n" +
				"        font-weight: inherit !important;\n" +
				"        line-height: inherit !important;\n" +
				"        text-decoration: none !important;\n" +
				"      }\n" +
				"      #MessageViewBody a {\n" +
				"        color: inherit;\n" +
				"        text-decoration: none;\n" +
				"        font-size: inherit;\n" +
				"        font-family: inherit;\n" +
				"        font-weight: inherit;\n" +
				"        line-height: inherit;\n" +
				"      }\n" +
				"      .btn-primary table td:hover {\n" +
				"        background-color: #34495e !important;\n" +
				"      }\n" +
				"      .btn-primary a:hover {\n" +
				"        background-color: #34495e !important;\n" +
				"        border-color: #34495e !important;\n" +
				"      }\n" +
				"    }\n" +
				"    </style>\n" +
				"  </head>\n" +
				"  <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
				"    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n" +
				"      <tr>\n" +
				"        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
				"        <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n" +
				"          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n" +
				"\n" +
				"            <!-- START CENTERED WHITE CONTAINER -->\n" +
				"            <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\">Regalo de libro.</span>\n" +
				"            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">\n" +
				"\n" +
				"              <!-- START MAIN CONTENT AREA -->\n" +
				"              <tr>\n" +
				"                <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\n" +
				"                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
				"                    <tr>\n" +
				"                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
				"                        <h3 style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Regalo de libro</h3>\n" +
				"                        <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">" + publicacion.getLibro().getNombre() + "</p>\n" +
				"                        <img src=\"cid:Image\"  width=\"50%\" height=\"50%\"/>" +
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\n" +
				"                          <tbody>\n" +
				"                            <tr>\n" +
				"                              <td align=\"left\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\">\n" +
				"                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">\n" +
				"                                  <tbody>\n" +
				"                                    <tr>\n" +
				"                                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a href=\"http://localhost:8080/biblioteca\" target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">Ver Libros</a> </td>\n" +
				"                                    </tr>\n" +
				"                                  </tbody>\n" +
				"                                </table>\n" +
				"                              </td>\n" +
				"                            </tr>\n" +
				"                          </tbody>\n" +
				"                        </table>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                  </table>\n" +
				"                </td>\n" +
				"              </tr>\n" +
				"\n" +
				"            <!-- END MAIN CONTENT AREA -->\n" +
				"            </table>\n" +
				"\n" +
				"            <!-- START FOOTER -->\n" +
				"            <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">\n" +
				"              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
				"                <tr>\n" +
				"                  <td class=\"content-block\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
				"                  </td>\n" +
				"                </tr>\n" +
				"                <tr>\n" +
				"                  <td class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
				"                    Powered by <a href=\"http://localhost:8080\" style=\"color: #999999; font-size: 12px; text-align: center; text-decoration: none;\">404 NOT FOUND</a>.\n" +
				"                  </td>\n" +
				"                </tr>\n" +
				"              </table>\n" +
				"            </div>\n" +
				"            <!-- END FOOTER -->\n" +
				"\n" +
				"          <!-- END CENTERED WHITE CONTAINER -->\n" +
				"          </div>\n" +
				"        </td>\n" +
				"        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
				"      </tr>\n" +
				"    </table>\n" +
				"  </body>\n" +
				"</html>";
		this.enviarEmail(beneficiado, asunto, texto, imagen, pdf);
	}

}