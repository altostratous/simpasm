module instmemory( write, addr, datain, dataout, clk, reset);
	input [31:0] datain;
	input [15:0] addr;
	input write, clk, reset;
	output [31:0] dataout;
	
	reg[31:0] mem[255 : 0];
	
	assign dataout<= mem[addr];
	
	always @(posedge clk) begin
		if(reset) begin
{MEMORY}
		end
		else begin
			if(write==1)
				mem[addr]<=datain;
			
		end

	end
endmodule
