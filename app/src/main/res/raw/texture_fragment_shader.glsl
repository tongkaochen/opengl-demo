precision mediump float;
uniform vec4 u_Color;
varying vec2 v_TextureCoord;
uniform sampler2D u_Sampler_Texture;
void main() {
    //gl_FragColor = u_Color;
    gl_FragColor = texture2D(u_Sampler_Texture, v_Texture_Coord)
}