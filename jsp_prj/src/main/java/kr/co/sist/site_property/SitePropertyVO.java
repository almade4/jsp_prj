package kr.co.sist.site_property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SitePropertyVO {
	private String protocol, server_name, server_port, context_root,
					upload_dir, api_key, site_info;

}
