provider "oci" {}

data "template_file" "cloud-config" {
  template = <<-EOT
   #cloud-config
package_upgrade: true
packages:
- yum-utils
- curl
write_files:
- content: |
    #!/bin/bash
    PUBLIC_IP=$(curl -s https://ifconfig.me/)
    echo "Public IP: $PUBLIC_IP" >> /tmp/ip
  path: /usr/local/bin/update_public_ip.sh
  permissions: '0755'
runcmd:
- yum update -y
- /usr/local/bin/update_public_ip.sh
- systemctl stop firewalld
- systemctl disable firewalld
- curl -fsSL https://tailscale.com/install.sh | sh
- tailscale up --authkey=YOUR_AUTH_KEY
- curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC="server --tls-san $(cat /tmp/ip | grep 'Public IP' | awk '{print $3}')" INSTALL_K3S_VERSION=v1.26.7+k3s1 sh -s -
- reboot

  EOT
}

resource "oci_core_instance" "generated_oci_core_instance" {
	agent_config {
		is_management_disabled = "false"
		is_monitoring_disabled = "false"
		plugins_config {
			desired_state = "DISABLED"
			name = "Vulnerability Scanning"
		}
		plugins_config {
			desired_state = "DISABLED"
			name = "Oracle Java Management Service"
		}
		plugins_config {
			desired_state = "ENABLED"
			name = "OS Management Service Agent"
		}
		plugins_config {
			desired_state = "ENABLED"
			name = "Compute Instance Run Command"
		}
		plugins_config {
			desired_state = "ENABLED"
			name = "Compute Instance Monitoring"
		}
		plugins_config {
			desired_state = "DISABLED"
			name = "Block Volume Management"
		}
		plugins_config {
			desired_state = "DISABLED"
			name = "Bastion"
		}
	}
	availability_config {
		recovery_action = "RESTORE_INSTANCE"
	}
	availability_domain = "Pnha:AP-TOKYO-1-AD-1"
	compartment_id = "ocid1.tenancy.oc1..aaaaaaaa6fgxhzuyyhpitgy2iequp5cx26wifsqjxglvpalcrlhltdtxf5qq"
	create_vnic_details {
		assign_private_dns_record = "true"
		assign_public_ip = "true"
		subnet_id = "ocid1.subnet.oc1.ap-tokyo-1.aaaaaaaa3l7ipslem743qrdsae7vqo7v3f22y3vgay6rwm3zjoeums2ldhgq"
	}
	display_name = "arm1"
	instance_options {
		are_legacy_imds_endpoints_disabled = "false"
	}
	is_pv_encryption_in_transit_enabled = "true"
	metadata = {
		"ssh_authorized_keys" = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCEATLwB8zEBSHmEVEUW0YD1TgkiKSvqzxwNIv3iIomZ3h0L39b98xrWRfNI46QYLKCeytZHqHsRTKH/PjkEUvalNs5ciK2kWdGypfX+1btcKM+xu7wrUeA/yGPLrrquD0F0BYOwE2JLs6+NN5gwzEuFF84UaYyE9KnTTakcKRZdSsUnhfym2DLqWSjgvuXc9oJJ4R+ioCXtFu9bTByMctbIByIqZrpDtubtgfADZvl23BvtWxuLb4U5qvcoOzk0Xucluukvglmw/ORQW5VE4qZl8gjap5qZY1DAZKdP7oXhS9NALBVrD1IJuvKwqQ5bril5Oa6esSSTwBqlmalVGGL",
        "user_data" = "${base64encode(data.template_file.cloud-config.rendered)}"
	}
	shape = "VM.Standard.A1.Flex"
	shape_config {
		memory_in_gbs = "12"
		ocpus = "2"
	}
	source_details {
		source_id = "ocid1.image.oc1.ap-tokyo-1.aaaaaaaautx4223ck2nhmfaaypfx37a2h462ytul2hvly6xyl3o47wnjh2fa"
		source_type = "image"
	}
}
