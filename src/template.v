module instmemory( write, addr, datain, dataout, clk, reset);
	input [31:0] datain;
	input [15:0] addr;
	input write, clk, reset;
	output reg[31:0] dataout;
	
	reg[31:0] mem[255 : 0];
	
	always @(posedge clk) begin
		if(reset) begin
{MEMORY}
		end
		else begin
			if(write==1)
				mem[addr]<=datain;
			dataout<= mem[addr];
		end

	end
endmodule
