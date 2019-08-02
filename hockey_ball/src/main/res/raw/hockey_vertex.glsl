attribute vec4 a_Position;
attribute vec4 a_Color;
uniform mat4 m_Projection;
varying vec4 v_Color;
void main() {
    gl_Position = m_Projection * a_Position;
    v_Color = a_Color;
    gl_PointSize = 30.0;
}